package io.andrelucas.solaceevents.workstation.business;

public class CreateWorkstationException extends RuntimeException {

  public CreateWorkstationException(String message, Throwable cause) {
    super(message, cause);
  }

  public CreateWorkstationException(String message) {
    super(message);
  }
}
