package io.andrelucas.solaceevents.workstation.dataprovider;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkstationEntityRepository extends MongoRepository<WorkstationEntity, String> {

}
