package io.andrelucas.solaceevents.workstation.business;


import io.andrelucas.solaceevents.EventCarriedPublisher;
import io.andrelucas.solaceevents.workstation.business.WorkstationEventCarriedPublisher.WorkerStationEvent;
import java.time.ZonedDateTime;
import java.util.UUID;

public interface WorkstationEventCarriedPublisher extends
    EventCarriedPublisher<WorkerStationEvent> {

  record WorkerStationEvent(UUID entityId, Workstation workstation, ZonedDateTime publishDateTime) {

    public WorkerStationEvent {
      if (entityId == null) {
        throw new IllegalArgumentException("entityId cannot be null");
      }
      if (workstation == null) {
        throw new IllegalArgumentException("workstation cannot be null");
      }
    }

    public WorkerStationEvent(UUID entityId, Workstation workstation) {
      this(entityId, workstation, ZonedDateTime.now());
    }
  }
}
