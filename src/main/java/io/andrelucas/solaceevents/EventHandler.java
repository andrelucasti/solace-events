package io.andrelucas.solaceevents;

import com.solacesystems.jcsmp.SessionEventArgs;
import com.solacesystems.jcsmp.SessionEventHandler;
import org.springframework.stereotype.Component;

@Component
public class EventHandler implements SessionEventHandler {

  @Override
  public void handleEvent(final SessionEventArgs sessionEventArgs) {
    System.out.println("Session event: " + sessionEventArgs.getEvent());
  }
}
