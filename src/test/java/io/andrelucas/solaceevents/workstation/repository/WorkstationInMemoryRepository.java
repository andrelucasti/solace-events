package io.andrelucas.solaceevents.workstation.repository;

import io.andrelucas.solaceevents.workstation.business.Workstation;
import io.andrelucas.solaceevents.workstation.business.WorkstationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WorkstationInMemoryRepository implements WorkstationRepository {
  Map<UUID, Workstation> workstations = new HashMap<>();

  @Override
  public void save(Workstation entity) {
    workstations.put(entity.id(), entity);
  }

  @Override
  public void update(Workstation entity) {

  }

  @Override
  public void delete(Workstation entity) {

  }

  @Override
  public Optional<Workstation> findById(UUID uuid) {
    if (workstations.containsKey(uuid)) {
      return Optional.of(workstations.get(uuid));
    }

    return Optional.empty();
  }

  @Override
  public List<Workstation> findAll() {
    return new ArrayList<>(workstations.values());
  }
}
