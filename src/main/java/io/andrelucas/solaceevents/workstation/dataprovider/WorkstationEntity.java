package io.andrelucas.solaceevents.workstation.dataprovider;


import io.andrelucas.solaceevents.workstation.business.Geolocation;
import io.andrelucas.solaceevents.workstation.business.Workstation.WorkstationState;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Workstation")
public class WorkstationEntity {

  @Id
  private UUID id;
  private String name;
  private Geolocation geolocation;

  @Version
  private int version;
  private WorkstationState state;

  @CreatedDate
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public WorkstationEntity(final UUID id, final String name, final Geolocation geolocation, final WorkstationState state) {
    this.id = id;
    this.name = name;
    this.geolocation = geolocation;
    this.state = state;
    this.updatedDate = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Geolocation getGeolocation() {
    return geolocation;
  }

  public int getVersion() {
    return version;
  }

  public WorkstationState getState() {
    return state;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public LocalDateTime getUpdatedDate() {
    return updatedDate;
  }
}
