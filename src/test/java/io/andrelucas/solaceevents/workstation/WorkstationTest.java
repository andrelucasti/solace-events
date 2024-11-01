package io.andrelucas.solaceevents.workstation;

import static org.junit.jupiter.api.Assertions.*;

import io.andrelucas.solaceevents.workstation.business.Geolocation;
import io.andrelucas.solaceevents.workstation.business.Workstation;
import org.junit.jupiter.api.Test;


class WorkstationTest {

  @Test
  void shouldReturnWorkstationWhenItIsCreated() {
    final var geolocation = new Geolocation(89, 111);
    final var workstation = Workstation.create("Workstation 1", geolocation);

    assertEquals("Workstation 1", workstation.name());
    assertEquals(89, workstation.location().latitude());
    assertEquals(111, workstation.location().longitude());
  }

  @Test
  void shouldThrowExceptionWhenGeolocationIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> Workstation.create("Workstation 1", new Geolocation(91, 0)));
    assertThrows(IllegalArgumentException.class, () -> Workstation.create("Workstation 1", new Geolocation(0, 181)));
  }

  @Test
  void shouldReturnWorkstationInactiveWhenItIsCreated() {

    final var geolocation = new Geolocation(89, 111);
    final var workstation = Workstation.create("Workstation 1", geolocation);

    assertEquals(Workstation.WorkstationState.INACTIVE, workstation.state());
  }
}