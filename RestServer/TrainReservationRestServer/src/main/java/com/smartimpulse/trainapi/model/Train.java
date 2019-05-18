package com.smartimpulse.trainapi.model;

import org.springframework.data.annotation.Id;

public class Train {
	@Id
	private String id;
	private String name;
	private String arrival;
	private String depature;
	private String arrivalTime;
	private String depatureTime;
	private boolean isActive;
	
	public Train() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param arrival
	 * @param depature
	 * @param arrivalTime
	 * @param depatureTime
	 * @param isActive
	 */
	public Train(String id, String name, String arrival, String depature, String arrivalTime, String depatureTime,
			boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.arrival = arrival;
		this.depature = depature;
		this.arrivalTime = arrivalTime;
		this.depatureTime = depatureTime;
		this.isActive = isActive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepatureTime() {
		return depatureTime;
	}

	public void setDepatureTime(String depatureTime) {
		this.depatureTime = depatureTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDepature() {
		return depature;
	}

	public void setDepature(String depature) {
		this.depature = depature;
	}
	
}
