package com.smartimpulse.trainapi.model;

import org.springframework.data.annotation.Id;

public class Train {
	@Id
	private long id;
	private String name;
	private String arrivalTime;
	private String depatureTime;
	private boolean isActive;
	
	public Train() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param arrivalTime
	 * @param depatureTime
	 * @param isActive
	 */
	public Train(long id, String name, String arrivalTime, String depatureTime, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.arrivalTime = arrivalTime;
		this.depatureTime = depatureTime;
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		
}
