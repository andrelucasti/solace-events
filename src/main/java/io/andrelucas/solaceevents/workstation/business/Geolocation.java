package io.andrelucas.solaceevents.workstation.business;

public record Geolocation(double latitude, double longitude) {

  public Geolocation {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Invalid latitude");
    }
    if (longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Invalid longitude");
    }
  }
}
