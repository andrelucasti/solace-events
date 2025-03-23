package io.andrelucas.solaceevents;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.MissingResourcesCreationConfiguration.MissingResourcesCreationStrategy;
import com.solace.messaging.config.SolaceProperties.AuthenticationProperties;
import com.solace.messaging.config.SolaceProperties.ServiceProperties;
import com.solace.messaging.config.SolaceProperties.TransportLayerProperties;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.publisher.PersistentMessagePublisher;
import com.solace.messaging.receiver.MessageReceiver.MessageHandler;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;
import com.solace.messaging.resources.TopicSubscription;
import com.solacesystems.jcsmp.JCSMPProperties;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolaceNativeConfiguration {

  @Value("${solace.host}")
  private String host;

  @Value("${solace.username}")
  private String username;

  @Value("${solace.password}")
  private String password;

  @Value("${solace.vpn}")
  private String vpn;


  @Bean
  public JCSMPProperties properties() {
    final JCSMPProperties properties = new JCSMPProperties();
    properties.setProperty(JCSMPProperties.HOST, host);
    properties.setProperty(JCSMPProperties.USERNAME, username);
    properties.setProperty(JCSMPProperties.PASSWORD, password);
    properties.setProperty(JCSMPProperties.VPN_NAME, vpn);
    properties.setProperty(JCSMPProperties.REAPPLY_SUBSCRIPTIONS, true);

    return properties;
  }


  @Bean
  public MessagingService messagingService() {
    final var messagingService = MessagingService.builder(ConfigurationProfile.V1)
        .fromProperties(nativeProperties())
        .build();

    messagingService.connectAsync();

    messagingService.addServiceInterruptionListener(serviceEvent -> {
      System.out.println("Service event: " + serviceEvent);
    });

    messagingService.addReconnectionListener(reconnectionEvent -> {
      System.out.println("Reconnection event: " + reconnectionEvent);
    });

    messagingService.addReconnectionAttemptListener(reconnectionAttemptEvent -> {
      System.out.println("Reconnection attempt event: " + reconnectionAttemptEvent);
    });

    return messagingService;
  }

  @Bean
  public PersistentMessagePublisher persistentMessagePublisher(@Qualifier("messagingService") final MessagingService messagingService) {
    final var persistentMessagePublisher = messagingService.createPersistentMessagePublisherBuilder()
        .onBackPressureWait(1)
        .build();

    persistentMessagePublisher.startAsync();

    return persistentMessagePublisher;

  }

  @Bean
  public PersistentMessageReceiver persistentMessageReceiver(@Qualifier("messagingService") final MessagingService messagingService,
      @Qualifier("workstationShiftCreatedHandler") final MessageHandler handler) {
    final var queue = Queue.durableNonExclusiveQueue("test_shift_queue");
    final var persistentMessageReceiver = messagingService.createPersistentMessageReceiverBuilder()
        .withMissingResourcesCreationStrategy(MissingResourcesCreationStrategy.CREATE_ON_START)
        .withSubscriptions(TopicSubscription.of("workstation/created"))
        .fromProperties(nativeProperties())
        .build(queue);

    persistentMessageReceiver.startAsync();
    persistentMessageReceiver.receiveAsync(handler);

    return persistentMessageReceiver;
  }

  private Properties nativeProperties() {
    final Properties properties = new Properties();
    properties.setProperty(TransportLayerProperties.HOST, host);
    properties.setProperty(AuthenticationProperties.SCHEME_BASIC_USER_NAME, username);
    properties.setProperty(AuthenticationProperties.SCHEME_BASIC_PASSWORD, password);
    properties.setProperty(ServiceProperties.VPN_NAME, vpn);
    properties.setProperty(ServiceProperties.RECEIVER_DIRECT_SUBSCRIPTION_REAPPLY, "true");
    properties.setProperty(TransportLayerProperties.RECONNECTION_ATTEMPTS, "20");
    properties.setProperty(TransportLayerProperties.CONNECTION_RETRIES_PER_HOST, "5");

    return properties;
  }
}
