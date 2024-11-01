package io.andrelucas.solaceevents.workstation.thirdparty;

import io.andrelucas.solaceevents.workstation.business.WorkstationEventCarriedPublisher;
import java.util.LinkedList;
import java.util.Queue;
import org.springframework.stereotype.Service;

@Service
public class WorkstationEventCarriedPublisherImpl implements WorkstationEventCarriedPublisher {
  private final Queue<WorkerStationEvent> events = new LinkedList<>();

  @Override
  public void publish(final WorkerStationEvent event, final String topic) {
    events.add(event);
  }
}
