package com.smartimpulse.trainapi.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Train {
	@Id
	private long id;
	private String name;
	private List<String> arrivalTimes;
	private boolean isActive;
	
	public Train() {
		super();
	}
	public Train(long id, String name, List<String> arrivalTimes, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.arrivalTimes = arrivalTimes;
		this.isActive = isActive;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the arrivalTimes
	 */
	public List<String> getArrivalTimes() {
		return arrivalTimes;
	}
	/**
	 * @param arrivalTimes the arrivalTimes to set
	 */
	public void setArrivalTimes(List<String> arrivalTimes) {
		this.arrivalTimes = arrivalTimes;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
