package io.andrelucas.solaceevents.workstation.business;

import io.andrelucas.solaceevents.workstation.business.WorkstationEventCarriedPublisher.WorkerStationEvent;
import org.springframework.stereotype.Service;


@Service
public class CreateWorkstation {
  private final WorkstationRepository workstationRepository;
  private final WorkstationEventCarriedPublisher workstationEventCarriedPublisher;

  public CreateWorkstation(final WorkstationRepository workstationRepository,
      final WorkstationEventCarriedPublisher workstationEventCarriedPublisher) {

    this.workstationRepository = workstationRepository;
    this.workstationEventCarriedPublisher = workstationEventCarriedPublisher;
  }


  public void execute(final Input input) {
    try {
      final var location = new Geolocation(input.latitude(), input.longitude());
      final var workstation = Workstation.create(input.name(), location);

      workstationRepository.save(workstation);

      final var workerStationEvent = new WorkerStationEvent(workstation.id(), workstation);
      workstationEventCarriedPublisher.publish(workerStationEvent, "workstation/created");

    } catch (Exception e) {
      throw new CreateWorkstationException("Got an error at create an workstation", e);
    }
  }

  public record Input(String name, double latitude, double longitude) {}
}
