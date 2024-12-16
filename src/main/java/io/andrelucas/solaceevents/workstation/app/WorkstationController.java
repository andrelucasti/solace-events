package io.andrelucas.solaceevents.workstation.app;

import io.andrelucas.solaceevents.workstation.business.CreateWorkstation;
import io.andrelucas.solaceevents.workstation.business.CreateWorkstation.Input;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workstation")
public class WorkstationController {

  private final CreateWorkstation createWorkstation;

  public WorkstationController(final CreateWorkstation createWorkstation) {
    this.createWorkstation = createWorkstation;
  }

  @PostMapping
  public ResponseEntity<Void> createWorkstation(@RequestBody final Input input) {

    createWorkstation.execute(input);

    return ResponseEntity.status(HttpStatus.CREATED)
        .build();
  }
}
