package com.fireforce.fStation.serv;

import org.springframework.stereotype.Service;

import com.fireforce.fStation.repo.fStationRepo;

@Service
public class fStationServ {
	private final fStationRepo fSRepo;
	
	public fStationServ(fStationRepo fSRepo) {
		this.fSRepo = fSRepo;
	}
}
