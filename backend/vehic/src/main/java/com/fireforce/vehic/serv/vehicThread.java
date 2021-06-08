package com.fireforce.vehic.serv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fireforce.vehic.repo.vehicRepo;
import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;
import com.fireforce.vehic.model.vehic;

public class vehicThread implements Runnable {

	
	private vehicServ vServ;
	private vehicRepo vRepo;
	boolean isEnd = false;

	public vehicThread(vehicRepo vRepo,vehicServ vServ) {
		this.vRepo = vRepo;
		this.vServ = vServ;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(500);
				for (vehic v: vRepo.findAll()) {
					if (v.isMoving()) {
						
						if (v.getProgress()>1) {
							vServ.moveVehic(v.getEndPoint(), v.getId());
							v.setMoving(false);
						}
						else {
								Coord TravelCoord = new Coord();
								TravelCoord.setLat(v.getLat()+v.getDlLat());
								TravelCoord.setLon(v.getLon()+v.getDlLon());
								vServ.moveVehic(TravelCoord, v.getId());
								v.setProgress(v.getProgress()+v.getDl()/v.getD());

							
							
							
						
					}
					
					
				}
				}
				
				
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Runnable DisplayRunnable ends.... ");
	}

	public void stop() {
		this.isEnd = true;
	}

}
