package com.fireforce.vehic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleType;

@Entity
public class vehic {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	public static final int CREW_MEMBER_START_VALUE=-1;
	private double lon;
	private double lat;
	private VehicleType type;
	private float efficiency; // need all crew member to reach full efficiency value between 0 and 10
	private LiquidType liquidType; // type of liquid effective to type of fire
	private float liquidQuantity; // total quantity of liquid
	private float liquidConsumption; // per second when use
	private float fuel;		// total quantity of fuel
	private float fuelConsumption; // per km
	private int crewMember;
	private int crewMemberCapacity;
	private Integer facilityRefID;

	
	public vehic(double lon, double lat, com.project.model.dto.VehicleType type, float efficiency,
			com.project.model.dto.LiquidType liquidType, float liquidQuantity, float liquidConsumption, float fuel,
			float fuelConsumption, int crewMember, int crewMemberCapacity, Integer facilityRefID) {
		super();
		this.id=id;
		this.lon = lon;
		this.lat = lat;
		this.type = type;
		this.efficiency = efficiency;
		this.liquidType = liquidType;
		this.liquidQuantity = liquidQuantity;
		this.liquidConsumption = liquidConsumption;
		this.fuel = fuel;
		this.fuelConsumption = fuelConsumption;
		this.crewMember = crewMember;
		this.crewMemberCapacity = crewMemberCapacity;
		this.facilityRefID = facilityRefID;
	}
	
	public vehic() {}
	
	public String toString() {
		return "Vehicule " + "[" + this.id + "] : position (" + this.lon + " ; " + this.lat + "), type : " 
	+ this.type.toString() + ", efficiency : " + this.efficiency + ", liquid type : " + this.liquidType +
	", liquid quantity : " + this.liquidQuantity + ", liquid consumption : " + this.liquidConsumption +
	", fuel : " + this.fuel + ", fuel consumption : " + this.fuelConsumption + ", crew member : " 
	+ this.crewMember + ", crew member capacity : " + this.crewMemberCapacity + ", facility ref ID : "
	+ this.facilityRefID;
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

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public float getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	public LiquidType getLiquidType() {
		return liquidType;
	}

	public void setLiquidType(LiquidType liquidType) {
		this.liquidType = liquidType;
	}

	public float getLiquidQuantity() {
		return liquidQuantity;
	}

	public void setLiquidQuantity(float liquidQuantity) {
		this.liquidQuantity = liquidQuantity;
	}

	public float getLiquidConsumption() {
		return liquidConsumption;
	}

	public void setLiquidConsumption(float liquidConsumption) {
		this.liquidConsumption = liquidConsumption;
	}

	public float getFuel() {
		return fuel;
	}

	public void setFuel(float fuel) {
		this.fuel = fuel;
	}

	public float getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(float fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public int getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(int crewMember) {
		this.crewMember = crewMember;
	}

	public int getCrewMemberCapacity() {
		return crewMemberCapacity;
	}

	public void setCrewMemberCapacity(int crewMemberCapacity) {
		this.crewMemberCapacity = crewMemberCapacity;
	}

	public Integer getFacilityRefID() {
		return facilityRefID;
	}

	public void setFacilityRefID(Integer facilityRefID) {
		this.facilityRefID = facilityRefID;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
