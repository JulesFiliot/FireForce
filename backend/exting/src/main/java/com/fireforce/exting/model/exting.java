package com.fireforce.exting.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class exting {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	public exting (String name) {
		super();
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
