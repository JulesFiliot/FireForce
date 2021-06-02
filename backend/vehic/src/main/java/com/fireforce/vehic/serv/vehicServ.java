package com.fireforce.vehic.serv;

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
		System.out.println("je suis dans la fonction generate");
		vehic v1 = new vehic(0,0, VehicleType.PUMPER_TRUCK, 12, LiquidType.CARBON_DIOXIDE, 1, 1, 1, 1, 1, 1, 1);
		vehic v2 = new vehic(0,0, VehicleType.FIRE_ENGINE, 12, LiquidType.POWDER, 1, 1, 1, 1, 1, 1, 1);
		this.addVehic(v1);
		this.addVehic(v2);
	}

}
