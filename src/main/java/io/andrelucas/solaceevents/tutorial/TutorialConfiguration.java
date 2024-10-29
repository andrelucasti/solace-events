package io.andrelucas.solaceevents.tutorial;

import com.solacesystems.jcsmp.Context;
import com.solacesystems.jcsmp.ContextProperties;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import com.solacesystems.jcsmp.XMLMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TutorialConfiguration {

  @Bean
  public JCSMPSession tutorialSession(@Qualifier("properties") JCSMPProperties properties) throws JCSMPException {
    ContextProperties contextProperties = new ContextProperties();
    contextProperties.setName("tutorialContext");

    Context context = JCSMPFactory.onlyInstance().createContext(contextProperties);

    var session = JCSMPFactory.onlyInstance().createSession(properties, context,
        sessionEventArgs -> System.out.println("Session event: " + sessionEventArgs.getEvent()));

    session.connect();

    return session;
  }

  @Bean(destroyMethod = "close")
  public XMLMessageConsumer tutorialListenerConfiguration(
      @Qualifier("tutorialSession") JCSMPSession session,
      @Qualifier("tutorialListener") XMLMessageListener shiftListener) throws JCSMPException {

    return createMessageConsumer(session, shiftListener, "tutorial/topic");
  }

  private XMLMessageConsumer createMessageConsumer(JCSMPSession session, XMLMessageListener listener, String topicName) throws JCSMPException {
    var messageConsumer = session.getMessageConsumer(listener);
    var topic = JCSMPFactory.onlyInstance().createTopic(topicName);
    session.addSubscription(topic);

    messageConsumer.start();

    return messageConsumer;
  }
}
