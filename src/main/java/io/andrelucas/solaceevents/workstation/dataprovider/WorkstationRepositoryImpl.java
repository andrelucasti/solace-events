package io.andrelucas.solaceevents.workstation.dataprovider;

import io.andrelucas.solaceevents.workstation.business.Workstation;
import io.andrelucas.solaceevents.workstation.business.WorkstationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class WorkstationRepositoryImpl implements WorkstationRepository {

  private final WorkstationEntityRepository workstationEntityRepository;

  public WorkstationRepositoryImpl(final WorkstationEntityRepository workstationEntityRepository) {
    this.workstationEntityRepository = workstationEntityRepository;
  }

  @Override
  public void save(final Workstation workstation) {
    final var entity = new WorkstationEntity(workstation.id(), workstation.name(), workstation.location(), workstation.state());

    workstationEntityRepository.save(entity);

  }

  @Override
  public void update(final Workstation workstation) {

  }

  @Override
  public void delete(final Workstation workstation) {

  }

  @Override
  public Optional<Workstation> findById(final UUID uuid) {
    return Optional.empty();
  }

  @Override
  public List<Workstation> findAll() {
    return workstationEntityRepository.findAll()
        .stream()
        .map(entity -> new Workstation(entity.getId(), entity.getName(), entity.getGeolocation(), entity.getState()))
        .toList();
  }
}
