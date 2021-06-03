package com.fireforce.vehic.serv;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fireforce.vehic.model.vehic;
import com.fireforce.vehic.repo.vehicRepo;
import com.project.model.dto.Coord;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

@Service
public class vehicServ {
	
	@Autowired
	private final vehicRepo vRepo;

	public vehicServ(vehicRepo vRepo) {
		this.vRepo = vRepo;
	}
	
	public void addVehic(vehic v) {
		VehicleDto t = new VehicleDto(0,v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());
    	
		String reqUrl = "http://127.0.0.1:8081/vehicle";
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<VehicleDto> reqVehicDTO = restTemplate.postForEntity(reqUrl, t,VehicleDto.class);
		VehicleDto xv = reqVehicDTO.getBody();
		
		v.setRemoteId(xv.getId());
		vehic createdVehic = vRepo.save(v);
		
		System.out.println(createdVehic);
	}

	public void generateVehics() {
		vehic v1 = new vehic(0,0, VehicleType.PUMPER_TRUCK, 12, LiquidType.CARBON_DIOXIDE, 1, 1, 1, 1, 1, 1, 1);
		vehic v2 = new vehic(0,0, VehicleType.FIRE_ENGINE, 12, LiquidType.POWDER, 1, 1, 1, 1, 1, 1, 1);
		this.addVehic(v1);
		this.addVehic(v2);
	}
	
	public void updateVehic(vehic v) {
		VehicleDto t = new VehicleDto(v.getRemoteId(),v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());
    	String reqUrl = "http://127.0.0.1:8081/vehicle/"+v.getRemoteId();
        RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(reqUrl, t);
		
		vehic newV = getVehic(v.getId());
		newV = v;
		vRepo.save(newV);
	}
	
	public vehic getVehic(Integer id) {
		Optional<vehic> vOpt = vRepo.findById(id);
		if (vOpt.isPresent()) return vOpt.get();
		else return null;
	}
	
	public void newCoord(vehic v, double lat, double lon) {
		v.setLat(lat);
		v.setLon(lon);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newLiquidType(vehic v, String type) {
		if (type.equals("A")) v.setLiquidType(LiquidType.ALL);
		else if (type.equals("W")) v.setLiquidType(LiquidType.WATER);
		else if (type.equals("WA")) v.setLiquidType(LiquidType.WATER_WITH_ADDITIVES);
		else if (type.equals("C")) v.setLiquidType(LiquidType.CARBON_DIOXIDE);
		else if (type.equals("P")) v.setLiquidType(LiquidType.POWDER);
		else {
			System.out.println("Error : unknown liquid type.");
			return;
		}
		vRepo.save(v);
		System.out.println(v);
	}

	public void newEfficiency(vehic v, float val) {
		v.setEfficiency(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newLiquidQuantity(vehic v, float val) {
		v.setLiquidQuantity(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newLiquidConsumption(vehic v, float val) {
		v.setLiquidConsumption(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newFuel(vehic v, float val) {
		v.setFuel(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newFuelConsumption(vehic v, float val) {
		v.setFuelConsumption(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newCrewMember(vehic v, int val) {
		v.setCrewMember(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newCrewMemberCapacity(vehic v, int val) {
		v.setCrewMemberCapacity(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void moveVehic(Coord c, Integer id) {
		vehic v = getVehic(id);

		System.out.println(c);
		
		v.setLat(c.getLat());
		v.setLon(c.getLon());
		updateVehic(v);
		
		System.out.println(v);
	}

}
