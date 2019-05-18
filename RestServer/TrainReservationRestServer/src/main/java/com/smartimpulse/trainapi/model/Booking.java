package com.smartimpulse.trainapi.model;

import org.springframework.data.annotation.Id;

public class Booking {
	@Id
	private long id;
	private String personId;
	private String trainId;
	private double price;
	private boolean isPaid;
	private boolean isGovernment;
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
	public Booking(long id, String personId,String trainId, double price, boolean isPaid, boolean isGovernment) {
		super();
		this.id = id;
		this.personId = personId;
		this.trainId = trainId;
		this.price = price;
		this.isPaid = isPaid;
		this.isGovernment = isGovernment;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
}
