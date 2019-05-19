package com.smartimpulse.trainapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.smartimpulse.trainapi.model.User;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface UserRepository extends MongoRepository<User, String> {
	User findByUsername(String username);
}
