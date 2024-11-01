package io.andrelucas.solaceevents.workstation.business;

import java.util.UUID;

public record Workstation(UUID id, String name, Geolocation location, WorkstationState state) {

  public static Workstation create(final String name, final Geolocation location) {
    return new Workstation(UUID.randomUUID(), name, location, WorkstationState.INACTIVE);
  }

  public enum WorkstationState {
    ACTIVE, INACTIVE
  }
}
