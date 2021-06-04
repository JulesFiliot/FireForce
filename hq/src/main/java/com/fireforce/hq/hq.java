package com.fireforce.hq;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class hq {

	
	@Id
	@GeneratedValue
	private Integer id;

	public hq() {
		super();
	}
	


	public String toString() {
		return "HEADQUARTER [" + this.id + "]";
	}
	


	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id=id;
	}
	
	
}
