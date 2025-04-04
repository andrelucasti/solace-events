package io.andrelucas.solaceevents.workstation.thirdparty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solace.messaging.MessagingService;
import com.solace.messaging.publisher.OutboundMessage;
import com.solace.messaging.publisher.PersistentMessagePublisher;
import com.solace.messaging.resources.Topic;
import io.andrelucas.solaceevents.workstation.business.WorkstationEventCarriedPublisher;
import org.springframework.stereotype.Service;

@Service
public class WorkstationEventCarriedPublisherImpl implements WorkstationEventCarriedPublisher {
  private final PersistentMessagePublisher publisher;
  private final ObjectMapper objectMapper;
  private final MessagingService messagingService;

  public WorkstationEventCarriedPublisherImpl(final PersistentMessagePublisher publisher, final ObjectMapper objectMapper,
      MessagingService messagingService) {
    this.publisher = publisher;
    this.objectMapper = objectMapper;
    this.messagingService = messagingService;
  }

  @Override
  public void publish(final WorkerStationEvent event, final String topicName) {
    try {
      final var payload = objectMapper.writeValueAsString(event);
      final var topic = Topic.of(topicName);

      final var outboundMessage = messagingService.messageBuilder()
          .build(payload);

      publisher.publish(outboundMessage, topic);

    } catch (Exception e) {
      throw new RuntimeException("Got an error at publish an event", e);
    }
  }
}
