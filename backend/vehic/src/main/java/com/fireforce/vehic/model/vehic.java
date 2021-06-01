package com.fireforce.vehic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class vehic {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	public vehic (String name) {
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
