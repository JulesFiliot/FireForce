package com.fireforce.fire.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class fire {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	public fire (Integer id, String name) {
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
