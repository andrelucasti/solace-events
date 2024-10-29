package io.andrelucas.solaceevents.shift;

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
public class ShiftConfiguration {

  @Bean()
  public JCSMPSession shiftSession(@Qualifier("properties") JCSMPProperties properties) throws JCSMPException {
    var session = JCSMPFactory.onlyInstance().createSession(properties, null,
        sessionEventArgs -> System.out.println("Shift Session event: " + sessionEventArgs.getEvent()));

    session.connect();

    return session;
  }

  @Bean(destroyMethod = "close")
  public XMLMessageConsumer shiftListenerConfiguration(
      @Qualifier("shiftSession") JCSMPSession session,
      @Qualifier("shiftListener") XMLMessageListener shiftListener) throws JCSMPException {

    return createMessageConsumer(session, shiftListener, "shift/topic");
  }

  private XMLMessageConsumer createMessageConsumer(JCSMPSession session, XMLMessageListener listener, String topicName) throws JCSMPException {
    var messageConsumer = session.getMessageConsumer(listener);
    var topic = JCSMPFactory.onlyInstance().createTopic(topicName);
    session.addSubscription(topic);

    messageConsumer.start();

    return messageConsumer;
  }
}
