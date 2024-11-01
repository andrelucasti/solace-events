package io.andrelucas.solaceevents;

public interface EventCarriedPublisher<E>{
  void publish(E event, String topic);
}
