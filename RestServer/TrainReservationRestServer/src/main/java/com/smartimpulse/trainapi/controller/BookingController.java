package com.smartimpulse.trainapi.controller;

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
import com.smartimpulse.trainapi.service.EmailService;

@RestController
@RequestMapping("people/{id}/bookings/")
public class BookingController {
	@Autowired
	private BookingRepository repository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private EmailService emailService;
	
	@GetMapping
	public List<Booking> GetBookings(@PathVariable String id) {
		return repository.findAllByPersonId(id);
	}
	
	@PostMapping
	public Booking AddBooking(@PathVariable String id, @RequestBody Booking booking) {
		Person person = personRepository.findById(id).orElseThrow();
		Train train = trainRepository.findById(booking.getTrainId()).orElseThrow();
		booking.setPersonId(id);
		booking.setPaid(false);
		booking.setGovernment(false);
		booking = repository.insert(booking);
		try {
			MessagingController controller = new MessagingController();
			controller.sendSms(new MessageFormat(
					"Hi "+person.getFirstName()+",\n Your Booking has been completed under the ID "+booking.getId()+" in the train "+train.getName(), 
					"Best Train Reservation Service", 
					person.getTelNo()
					));
		}catch(Exception e) {}
		try {
			emailService.sendMail(person.getEmail(), "Successful Booking", "Please be informed that your booking was done successfully with the id of "+booking.getId()+" and will be confirmed in a while");
		}catch(Exception e) {}
		return booking;
	}
	
	@PutMapping
	public Booking UpdateBooking(@PathVariable String id, @RequestBody Booking booking) {
		booking.setPersonId(id);
		Optional<Booking> oldBooking = repository.findById(booking.getId());
		if(oldBooking.isPresent()) {
			booking.setGovernment(oldBooking.get().isGovernment());
			booking.setPaid(oldBooking.get().isPaid());
		}
		return repository.save(booking);
	}
	
	@GetMapping("{bid}/")
	public Optional<Booking> GetBooking(@PathVariable String id, @PathVariable String bid) {
		return repository.findById(bid);
	}
	
	@DeleteMapping("{bid}/")
	public void DeleteBooking(@PathVariable String id, @PathVariable String bid) {
		repository.deleteById(bid);
	}
	
	@PostMapping("{bid}/verify/govenment")
	public Booking SetBookingGoverment(@PathVariable String id, @PathVariable String bid, @RequestBody String NIC) {
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
		Person person = personRepository.findById(id).orElseThrow();
		if(NICs.contains(NIC)) {
			booking.setGovernment(true);
			//emailService.sendMail(person.getEmail(), "Successfully Confirmed Government Job", "Please be informed that your job is successfully confirmed as government.");
			repository.save(booking);
			return booking;
		}
		System.out.println(NIC);
		return null;
	}
	
	@PostMapping("{bid}/verify/payment")
	public void SetBookingPayment(
			@PathVariable String id,
			@PathVariable String bid,
			@RequestParam String cardNo,
			@RequestParam short cvc,
			@RequestParam String exp,
			@RequestParam String cName) {
		List<String> CCs = Arrays.asList(new String[] {
				"Sachith Rukshan,1234 5678 9012 3456,354,12/05",
				"Sasindu Lakshitha,1934 5578 9212 3456,384,02/05",
				"Dinali Sewwandi,1234 5678 9012 3456,354,11/05",
				"Gnana Paala,1234 5678 9012 3456,354,12/10"
		});
		Person person = personRepository.findById(id).orElseThrow();
		Booking booking = repository.findById(bid).orElseThrow();
		
		if(CCs.contains(String.join(",",cName, cardNo,Short.toString(cvc),exp))) {
			booking.setPaid(true);
			//emailService.sendMail(person.getEmail(), "Successfully Confirmed Your Payment", "Please be informed that your payment is successfully confirmed and you will recieve your ticket as promised.");
		}
		repository.save(booking);
	}
	
}
