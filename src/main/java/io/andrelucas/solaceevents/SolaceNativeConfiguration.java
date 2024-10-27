package io.andrelucas.solaceevents;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.SessionEventArgs;
import com.solacesystems.jcsmp.SessionEventHandler;
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

    return properties;
  }


  @Bean
  public JCSMPSession session(@Qualifier("properties") JCSMPProperties properties) throws JCSMPException {
    var session = JCSMPFactory.onlyInstance().createSession(properties, null,
        sessionEventArgs -> System.out.println("Session event: " + sessionEventArgs.getEvent()));

    session.connect();

    return session;
  }
}
