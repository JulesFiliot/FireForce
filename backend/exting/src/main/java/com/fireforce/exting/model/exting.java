package com.fireforce.exting.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class exting {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	public exting (Integer id) {
		super();
		this.id = id;
	}
}
