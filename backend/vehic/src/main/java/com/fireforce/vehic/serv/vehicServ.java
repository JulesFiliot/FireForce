package com.fireforce.vehic.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fireforce.vehic.model.vehic;
import com.fireforce.vehic.repo.vehicRepo;

@Service
public class vehicServ {
	
	@Autowired
	vehicRepo vRepo;

	public void addVehic(vehic v) {
		vehic createdVehic = vRepo.save(v);
		System.out.println(createdVehic);
	}

	public void generateVehics() {
		//vehic v = new ;
		
	}

}
