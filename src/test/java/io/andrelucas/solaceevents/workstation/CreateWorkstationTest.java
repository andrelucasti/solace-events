package io.andrelucas.solaceevents.workstation;

import static org.mockito.Mockito.*;

import io.andrelucas.solaceevents.workstation.business.CreateWorkstation;
import io.andrelucas.solaceevents.workstation.business.CreateWorkstation.Input;
import io.andrelucas.solaceevents.workstation.business.CreateWorkstationException;
import io.andrelucas.solaceevents.workstation.business.WorkstationEventCarriedPublisher;
import io.andrelucas.solaceevents.workstation.business.WorkstationEventCarriedPublisher.WorkerStationEvent;
import io.andrelucas.solaceevents.workstation.business.WorkstationRepository;
import io.andrelucas.solaceevents.workstation.repository.WorkstationInMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Iterables;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateWorkstationTest {

  private CreateWorkstation subject;
  private WorkstationRepository repository;

  @Mock
  private WorkstationEventCarriedPublisher workStationEventPublisher;

  @BeforeEach
  void setUp() {
    repository = new WorkstationInMemoryRepository();
    subject = new CreateWorkstation(repository, workStationEventPublisher);
  }

  @Test
  void shouldCreateWorkstation() {
    final var input = new Input("Workstation 1", 89, 111);
    subject.execute(input);

    final var workstations = repository.findAll();
    Assertions.assertThat(workstations).hasSize(1);

    final var workstation = Iterables.firstOf(workstations);
    Assertions.assertThat(workstation.name()).isEqualTo("Workstation 1");
    Assertions.assertThat(workstation.location().latitude()).isEqualTo(89);
    Assertions.assertThat(workstation.location().longitude()).isEqualTo(111);
  }

  @Test
  void shouldPublishWorkerEventWhenItIsCreated() {
    final var workerStationEventArgumentCaptor = ArgumentCaptor.forClass(WorkerStationEvent.class);
    final var input = new Input("Workstation 1", 89, 111);

    subject.execute(input);

    verify(workStationEventPublisher).publish(workerStationEventArgumentCaptor.capture(), eq("workstation/created"));
    final var workStationEvent = workerStationEventArgumentCaptor.getValue();

    Assertions.assertThat(workStationEvent.entityId()).isEqualTo(workStationEvent.workstation().id());
    Assertions.assertThat(workStationEvent.workstation().location().latitude()).isEqualTo(input.latitude());
    Assertions.assertThat(workStationEvent.workstation().location().longitude()).isEqualTo(input.longitude());
    Assertions.assertThat(workStationEvent.workstation().name()).isEqualTo(input.name());

  }

  @Test
  void shouldNotPublishEventWhenThrowException() {
    final var input = new Input("Workstation 1", 91, 111);

    Assertions.assertThatThrownBy(() -> subject.execute(input))
        .isInstanceOf(CreateWorkstationException.class);

    verifyNoInteractions(workStationEventPublisher);
  }

  @Test
  void shouldNotPublishWhenGeoLocationIsInvalid() {
    final var input = new Input("Workstation 1", -89, 192);

    Assertions.assertThatThrownBy(() -> subject.execute(input))
        .isInstanceOf(CreateWorkstationException.class);

    verifyNoInteractions(workStationEventPublisher);
  }
}