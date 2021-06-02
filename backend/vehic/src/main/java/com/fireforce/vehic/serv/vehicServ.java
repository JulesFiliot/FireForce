package com.fireforce.vehic.serv;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fireforce.vehic.model.vehic;
import com.fireforce.vehic.repo.vehicRepo;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleType;

@Service
public class vehicServ {
	
	@Autowired
	private final vehicRepo vRepo;

	public vehicServ(vehicRepo vRepo) {
		this.vRepo = vRepo;
	}
	
	public void addVehic(vehic v) {
		vehic createdVehic = vRepo.save(v);
		System.out.println(createdVehic);
	}

	public void generateVehics() {
		vehic v1 = new vehic(0,0, VehicleType.PUMPER_TRUCK, 12, LiquidType.CARBON_DIOXIDE, 1, 1, 1, 1, 1, 1, 1);
		vehic v2 = new vehic(0,0, VehicleType.FIRE_ENGINE, 12, LiquidType.POWDER, 1, 1, 1, 1, 1, 1, 1);
		this.addVehic(v1);
		this.addVehic(v2);
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

}
