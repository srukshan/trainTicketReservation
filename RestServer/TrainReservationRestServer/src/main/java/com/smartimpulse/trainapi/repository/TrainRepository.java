package com.smartimpulse.trainapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.smartimpulse.trainapi.model.Train;

@RepositoryRestResource(collectionResourceRel = "trains", path = "trains")
public interface TrainRepository extends MongoRepository<Train, String> {}
