package io.andrelucas.solaceevents.tutorial;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TutorialConfiguration {

  @Bean(destroyMethod = "close")
  public XMLMessageConsumer tutorialSession(
      @Qualifier("session") JCSMPSession session,
      @Qualifier("tutorialListener") TutorialListener tutorialListener) throws JCSMPException {

    var messageConsumer = session.getMessageConsumer(tutorialListener);
    var topic = JCSMPFactory.onlyInstance().createTopic("tutorial/topic");
    session.addSubscription(topic);

    messageConsumer.start();

    return messageConsumer;
  }
}
