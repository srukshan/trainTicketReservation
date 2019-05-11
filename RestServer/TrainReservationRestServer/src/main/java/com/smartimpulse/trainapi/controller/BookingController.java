package com.smartimpulse.trainapi.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartimpulse.trainapi.model.Booking;
import com.smartimpulse.trainapi.model.Person;
import com.smartimpulse.trainapi.model.Train;
import com.smartimpulse.trainapi.repository.BookingRepository;
import com.smartimpulse.trainapi.repository.PersonRepository;
import com.smartimpulse.trainapi.repository.TrainRepository;

@RestController
@RequestMapping("people/{id}/bookings/")
public class BookingController {
	@Autowired
	private BookingRepository repository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private TrainRepository trainRepository;
	
	@GetMapping
	public List<Booking> GetBookings(@PathVariable long id) {
		return repository.findAllByPersonId(id);
	}
	
	@PostMapping
	public Booking AddBooking(@PathVariable long id, @RequestBody Booking booking) {
		Person person = personRepository.findById(id).orElseThrow();
		Train train = trainRepository.findById(booking.getTrainId()).orElseThrow();
		booking.setPersonId(id);
		booking.setPaid(false);
		booking.setGovernment(false);
		booking = repository.insert(booking);
		MessagingController controller = new MessagingController();
		controller.sendSms(new MessageFormat(
				"Hi "+person.getFirstName()+",\n Your Booking has been completed under the ID "+booking.getId()+" in the train "+train.getName(), 
				"Best Train Reservation Service", 
				person.getTelNo()
				));
		return booking;
	}
	
	@PutMapping
	public Booking UpdateBooking(@PathVariable long id, @RequestBody Booking booking) {
		booking.setPersonId(id);
		Optional<Booking> oldBooking = repository.findById(booking.getId());
		if(oldBooking.isPresent()) {
			booking.setGovernment(oldBooking.get().isGovernment());
			booking.setPaid(oldBooking.get().isPaid());
		}
		return repository.save(booking);
	}
	
	@GetMapping("{bid}/")
	public Optional<Booking> GetBooking(@PathVariable long id, @PathVariable long bid) {
		return repository.findById(bid);
	}
	
	@DeleteMapping("{bid}/")
	public void DeleteBooking(@PathVariable long id, @PathVariable long bid) {
		repository.deleteById(bid);
	}
	
	@PostMapping("{bid}/verify/govenment")
	public void SetBookingGoverment(@PathVariable long id, @PathVariable long bid, @RequestParam String NIC) {
		List<String> NICs = Arrays.asList(new String[] {
				"980346936V",
				"980346935V",
				"980346934V",
				"980346933V",
				"980346932V",
				"980030415V"
		});
		personRepository.findById(id).orElseThrow();
		Booking booking = repository.findById(bid).get();
		if(NICs.contains(NIC)) {
			booking.setGovernment(true);
		}
		repository.save(booking);
	}
}
