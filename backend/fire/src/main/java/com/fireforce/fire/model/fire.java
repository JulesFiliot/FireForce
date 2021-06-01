package com.fireforce.fire.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class fire {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	public fire (Integer id) {
		super();
		this.id = id;
	}
}
