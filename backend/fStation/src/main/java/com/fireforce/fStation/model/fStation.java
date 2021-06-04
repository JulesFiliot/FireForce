package com.fireforce.fStation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class fStation {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private int capacity;
	private double lon;
	private double lat;
	
	public fStation() {
	}

	public fStation(String name) {
		super();
		this.setName(name);
		this.capacity=100;
		this.lon=4.86904827217447;
		this.lat=45.78391737991209;
		}
	
	public fStation(String name,int capacity, double lon, double lat) {
		super();
		this.setName(name);
		this.capacity=capacity;
		this.lon=lon;
		this.lat=lat;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon=lon;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat=lat;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity=capacity;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String toString() {
		return "Station "+this.name + " [id:" + this.id + "] : position (" + this.lat + " ; " + this.lon + "), capacity : " 
	+ this.capacity;
	}
}
