package com.fireforce.fStation.serv;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fireforce.fStation.model.fStation;
import com.fireforce.fStation.repo.fStationRepo;

@Service
public class fStationServ {
	private final fStationRepo fSRepo;
	
	public fStationServ(fStationRepo fSRepo) {
		this.fSRepo = fSRepo;
	}
	
	public void add(fStation fS) {
		fStation createdStation=fSRepo.save(fS);
		System.out.println(createdStation);
	}
	
	public fStation getStation(int id) {
		Optional<fStation> fSOpt =fSRepo.findById(id);
		if (fSOpt.isPresent()) {
			return fSOpt.get();
		} else {
			return null;
		}
	}
}
