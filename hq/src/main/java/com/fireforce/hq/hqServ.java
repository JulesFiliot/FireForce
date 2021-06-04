package com.fireforce.hq;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fireforce.fStation.model.fStation;
import com.fireforce.hq.hq;
import com.fireforce.hq.hqRepo;
import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

public class hqServ {

	@Autowired
	private final hqRepo hqRepo;

	public hqServ(hqRepo hqRepo) {
		this.hqRepo = hqRepo;
	}	
	
	public void add(hq hq) {
		hq createdHQ=hqRepo.save(hq);
		System.out.println(createdHQ);
	}
	
	public hq getHQ(int id) {
		Optional<hq> hqOpt =hqRepo.findById(id);
		if (hqOpt.isPresent()) {
			return hqOpt.get();
		} else {
			return null;
		}
	}
	
	
	@Bean(initMethod="init")
	public void init() {
        hq hq1 = new hq();
        
        this.add(hq1);
	}
	
}
