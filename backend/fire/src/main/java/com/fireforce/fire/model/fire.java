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
	private Integer remoteId = -1;
	
	@Embedded
	private List<Integer> linkedVehic = new ArrayList<Integer>();


	public fire() {
		super();
	}
	
	public fire(Integer remoteId) {
		super();
		this.remoteId=remoteId;
	}
	
	public fire(List<Integer> linkedVehic) {
		super();
		this.linkedVehic=linkedVehic;
	}

	public String toString() {
		return "Fire [" + this.id + "] [" + this.remoteId + "] : list of linked vehicles :"+this.linkedVehic;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id=id;
	}
	

	public Integer getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Integer remoteId) {
		this.remoteId = remoteId;
	}

}
