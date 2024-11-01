package io.andrelucas.solaceevents.workstation;

import static org.junit.jupiter.api.Assertions.*;

import io.andrelucas.solaceevents.workstation.business.Geolocation;
import org.junit.jupiter.api.Test;

class GeolocationTest {

  @Test
  void shouldThrowExceptionWhenLatitudeIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> new Geolocation(91, 0));
  }

  @Test
  void shouldThrowExceptionWhenLongitudeIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> new Geolocation(0, 181));
  }

  @Test
  void shouldReturnGeolocationWhenLatitudeAndLongitudeIsValid() {
    Geolocation geolocation = new Geolocation(89, 111);
    assertEquals(89, geolocation.latitude());
    assertEquals(111, geolocation.longitude());
  }
}