package com.smartimpulse.trainapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
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
import com.smartimpulse.trainapi.model.User;
import com.smartimpulse.trainapi.model.Train;
import com.smartimpulse.trainapi.repository.BookingRepository;
import com.smartimpulse.trainapi.repository.UserRepository;
import com.smartimpulse.trainapi.repository.TrainRepository;
import com.smartimpulse.trainapi.service.AuthorizenetPaymentService;
import com.smartimpulse.trainapi.service.DialogPaymentService;
import com.smartimpulse.trainapi.service.EmailService;

@RestController
@RequestMapping("people/{id}/bookings/")
public class BookingController {
	@Autowired
	private BookingRepository repository;
	@Autowired
	private UserRepository personRepository;
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
		User person = personRepository.findById(id).orElseThrow();
		Train train = trainRepository.findById(booking.getTrainId()).orElseThrow();
		booking.setPrice(GetTrainPrice(train, false));
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
		Booking oldBooking = repository.findById(booking.getId()).orElseThrow();
		Train train = trainRepository.findById(booking.getTrainId()).orElse(trainRepository.findById(oldBooking.getTrainId()).orElseThrow());
		booking.setTrainId(train.getId());
		booking.setPrice(GetTrainPrice(train, oldBooking.isGovernment()));
		booking.setGovernment(oldBooking.isGovernment());
		booking.setPaid(oldBooking.isPaid());
		return repository.save(booking);
	}
	
	private double GetTrainPrice(Train t, boolean gov) {
		if(gov) {
			return 90.00;
		}else {
			return 100.00;
		}
	}
	
	@GetMapping("{bid}/")
	public Optional<Booking> GetBooking(@PathVariable String id, @PathVariable String bid) {
		return repository.findById(bid);
	}
	
	@DeleteMapping("{bid}/")
	public void DeleteBooking(@PathVariable String id, @PathVariable String bid) {
		repository.deleteById(bid);
	}
	
	@PostMapping("{bid}/discounts/govenment")
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
		User person = personRepository.findById(id).orElseThrow();
		if(booking.isGovernment()) {
			return booking;
		}
		if(NICs.contains(NIC)) {
			booking.setGovernment(true);
			booking.setPrice(booking.getPrice()*0.9);
			//emailService.sendMail(person.getEmail(), "Successfully Confirmed Government Job", "Please be informed that your job is successfully confirmed as government.");
			repository.save(booking);
			return booking;
		}
		System.out.println(NIC);
		return null;
	}
	
	@PostMapping("{bid}/payment/card")
	public void SetBookingCardPayment(
			@PathVariable String id,
			@PathVariable String bid,
			@RequestBody Map<String, Object> body) {
		
		User person = personRepository.findById(id).orElseThrow();
		Booking booking = repository.findById(bid).orElseThrow();
		AuthorizenetPaymentService paymentService = new AuthorizenetPaymentService();
		if(booking.isPaid()) {
			return;
		}
		if(paymentService.doPayment(
				body.get("cName").toString(), 
				body.get("cardNo").toString(),
				body.get("cvc").toString(),
				body.get("exp").toString(),
				booking.getPrice())) {
			booking.setPaid(true);
			//emailService.sendMail(person.getEmail(), "Successfully Confirmed Your Payment", "Please be informed that your payment is successfully confirmed and you will recieve your ticket as promised.");
		}
		repository.save(booking);
	}
	
	@PostMapping("{bid}/payment/dialog")
	public void SetBookingDialogPayment(
			@PathVariable String id,
			@PathVariable String bid,
			@RequestParam int pin) {
		
		User person = personRepository.findById(id).orElseThrow();
		Booking booking = repository.findById(bid).orElseThrow();
		DialogPaymentService paymentService = new DialogPaymentService();
		if(booking.isPaid()) {
			return;
		}
		if(paymentService.doPayment(person.getTelNo(), pin, Double.toString(booking.getPrice()))) {
			booking.setPaid(true);
			//emailService.sendMail(person.getEmail(), "Successfully Confirmed Your Payment", "Please be informed that your payment is successfully confirmed and you will recieve your ticket as promised.");
		}
		repository.save(booking);
	}
	
}
