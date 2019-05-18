package com.smartimpulse.trainapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartimpulse.trainapi.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
	public List<Booking> findAllByPersonId(String id);
}
