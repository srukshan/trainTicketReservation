package com.smartimpulse.trainapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Booking {
	@Id
	private String id;
	private String personId;
	private String trainId;
	private double price;
	private boolean isPaid;
	private boolean isGovernment;
	private String date;
	public Booking() {
		super();
	}
	/**
	 * @param id
	 * @param personId
	 * @param trainId
	 * @param price
	 * @param isPaid
	 * @param isGovernment
	 */
	public Booking(String id, String personId,String trainId, double price, boolean isPaid, boolean isGovernment, String date) {
		super();
		this.id = id;
		this.personId = personId;
		this.trainId = trainId;
		this.price = price;
		this.isPaid = isPaid;
		this.isGovernment = isGovernment;
		this.setDate(date);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public boolean isGovernment() {
		return isGovernment;
	}
	public void setGovernment(boolean isGovernment) {
		this.isGovernment = isGovernment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
