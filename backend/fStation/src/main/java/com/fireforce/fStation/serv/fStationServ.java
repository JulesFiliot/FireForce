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
	
	public void editStationName(int id, String name) {
		Optional<fStation> fSOpt =fSRepo.findById(id);
		if (fSOpt.isPresent()) {
			fStation fS=fSOpt.get();
			fS.setName(name);
			fSRepo.save(fS);
			System.out.println(fS);

		}
	}
	
	public void editStationCapacity(int id, int capacity) {
		Optional<fStation> fSOpt =fSRepo.findById(id);
		if (fSOpt.isPresent()) {
			fStation fS=fSOpt.get();
			fS.setCapacity(capacity);
			fSRepo.save(fS);
			System.out.println(fS);

		}
	}
	
	public void newCoord(fStation fS, double lat, double lon) {
		fS.setLat(lat);
		fS.setLon(lon);
		fSRepo.save(fS);
		System.out.println(fS);
	}
}
