package com.fireforce.fStation.serv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fireforce.fStation.model.fStation;
import com.fireforce.fStation.repo.fStationRepo;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleType;

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

	public void updateFS(fStation fS) {
		Optional<fStation> fSOpt =fSRepo.findById(fS.getId());
		if (fSOpt.isPresent()) {
			fStation fS2=fSOpt.get();
			fS2=fS;
			fSRepo.save(fS2);
			System.out.println(fS2);

		}
	}
	
	/*
	@Bean(initMethod="init")
	public void init() {
        fStation station1 = new fStation("Montgomery");
        fStation station2 = new fStation("Rina");
        
        this.add(station1);
        this.add(station2);
	}
	*/

	public ArrayList<fStation> getAllStation() {
		ArrayList<fStation> ListFStation = new ArrayList<fStation>();
		Iterable<fStation> allFStation = fSRepo.findAll();
		Iterator<fStation> iterator = allFStation.iterator();
		while(iterator.hasNext()) {
		    fStation it = iterator.next();
		    ListFStation.add(it);
		}
		return ListFStation;	
	}

	public void resetFStation() {
		ArrayList<fStation> L = getAllStation();
		for (fStation station : L) {
			fSRepo.delete(station);
		}
	}
}
