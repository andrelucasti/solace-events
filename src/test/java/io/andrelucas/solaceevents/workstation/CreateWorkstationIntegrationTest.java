package io.andrelucas.solaceevents.workstation;

import io.andrelucas.solaceevents.workstation.business.CreateWorkstation;
import io.andrelucas.solaceevents.workstation.business.CreateWorkstation.Input;
import io.andrelucas.solaceevents.workstation.business.WorkstationRepository;
import io.andrelucas.solaceevents.workstation.dataprovider.WorkstationEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.solace.SolaceContainer;

@SpringBootTest
class CreateWorkstationIntegrationTest {
  @Autowired
  private WorkstationEntityRepository workstationEntityRepository;


  @BeforeEach
  void setUp() {
    workstationEntityRepository.deleteAll();
  }

  //  @Container
//  private static MongoDBContainer mongoDBContainer = new MongoDBContainer()
//      .withExposedPorts(27017)
//      .withEnv("MONGO_INITDB_ROOT_USERNAME", "solace-events")
//      .withEnv("MONGO_INITDB_ROOT_PASSWORD", "solace-events")
//      .withEnv("MONGO_INITDB_DATABASE", "solace-events");
//
//  @Container
//  private static SolaceContainer solaceContainer = new SolaceContainer("solace/solace-pubsub-standard:latest")
//      .withCredentials("admin", "admin")
//      .withVpn("default")
//      .withExposedPorts(55554, 55555);

  @Autowired
  private WorkstationRepository workStationRepository;

  @Autowired
  private CreateWorkstation subject;


  @Test
  void shouldSaveWorkstation() {
    final var input = new Input("workstation 2", 89, 111);
    subject.execute(input);
    final var workStation = workStationRepository.findAll().stream().findFirst().get();

    Assertions.assertThat(workStation.location().latitude())
        .isEqualTo(input.latitude());

    Assertions.assertThat(workStation.location().longitude())
        .isEqualTo(input.longitude());

    Assertions.assertThat(workStation.name())
        .isEqualTo(input.name());
  }
}