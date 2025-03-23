package io.andrelucas.solaceevents.shift;

import com.solace.messaging.receiver.InboundMessage;
import com.solace.messaging.receiver.MessageReceiver.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WorkstationShiftCreatedHandler implements MessageHandler {
  private static final Logger log = LoggerFactory.getLogger(WorkstationShiftCreatedHandler.class);

  @Override
  public void onMessage(InboundMessage inboundMessage) {
    final var payload = inboundMessage.getPayloadAsString();
    log.info("Received message - {}", inboundMessage);
    log.info("Payload - {}", payload);
  }
}
