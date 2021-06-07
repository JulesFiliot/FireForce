package com.fireforce.fire.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.project.model.dto.FireType;

@Entity
public class fire {
	
	@Id
	private Integer id;
	private ArrayList<Integer> linkedVehic;


	public fire() {
		super();
		linkedVehic = new ArrayList<Integer>();
	}

	
	public fire(ArrayList<Integer> linkedVehic) {
		super();
		this.linkedVehic=linkedVehic;
	}

	public String toString() {
		return "Fire [" + this.id + "] : list of linked vehicles :"+this.linkedVehic;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id=id;
	}
	
	
	public ArrayList<Integer> getLinkedVehic(){
		if (this.linkedVehic != null) {
			ArrayList<Integer> newList = new ArrayList<>(this.linkedVehic);	
			return newList;
		}
		return new ArrayList<Integer>();
	}
	
	public void addLinkedVehic(Integer id) {
		this.linkedVehic.add(id);
		System.out.println(this.linkedVehic);
	}

}
