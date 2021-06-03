package com.fireforce.fire.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.project.model.dto.FireType;

@Entity
public class fire {
	
	@Id
	@GeneratedValue
	private Integer id;
	private Integer remoteId = -1;
	private String name;
	private FireType type;
	private float intensity;
	private float range;
	private double lon;
	private double lat;

	public fire() {
	}
	
	public fire(FireType type, float intensity, float range, double lon, double lat) {
		super();;
		this.type = type;
		this.intensity = intensity;
		this.range = range;
		this.lon = lon;
		this.lat = lat;
	}

	public String toString() {
		return "Fire [" + this.id + "] [" + this.remoteId + "] : position (" + this.lat + " ; " + this.lon
				+ "), type : " + this.type + ", intensity : " + this.intensity + ", range : " + this.range;
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
	
	public FireType getType() {
		return type;
	}
	public void setType(FireType type) {
		this.type = type;
	}
	public float getIntensity() {
		return intensity;
	}
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	public float getRange() {
		return range;
	}
	public void setRange(float range) {
		this.range = range;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}

	public Integer getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Integer remoteId) {
		this.remoteId = remoteId;
	}

}
