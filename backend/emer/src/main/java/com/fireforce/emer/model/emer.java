package com.fireforce.emer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class emer {


	@Id
	@GeneratedValue
	private Integer id;
	private int year;
	private int month;
	private int day;
	
	public emer() {
	}

	public emer(int id, int year, int month, int day) {
		super();
		this.id=id;
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
