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
	@Autowired
	private vehicRepo vRepo;
	boolean isEnd = false;

	public vehicThread(vehicRepo vRepo,vehicServ vServ) {
		this.vRepo = vRepo;
		this.vServ = vServ;
	}

	public vehicThread(vehicServ vServ) {
		this.vServ = vServ;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(1000);
				for (vehic v: vRepo.findAll()) {
					System.out.println("==============");
					System.out.println("v"+v.getId()+" isMoving ?"+v.isMoving());
					if (v.isMoving()) {
						System.out.println("v progress ?"+v.getProgress());
						if (v.getProgress()>1) {
							System.out.println("==> progress > 1 donc va move le vehic sur le point précis");
							vServ.moveVehic(v.getEndPoint(), v.getId());
							v.setMoving(false);
							v.setProgress(0);
							vRepo.save(v);
						}
						else {
								System.out.println("==> progress < 1 donc on bouge légèrement");
								Coord TravelCoord = new Coord();
								System.out.println("v.getLat :"+v.getLat()+", v.getDlLat:"+v.getDlLat()+", v.getLon :"+v.getLon()+", v.getDlLon:"+v.getDlLon());
								TravelCoord.setLat(v.getLat()+v.getDlLat());
								TravelCoord.setLon(v.getLon()+v.getDlLon());
								System.out.println("id du vehic qu'on va bouger :"+v.getId());
								vServ.moveVehic(TravelCoord, v.getId());
								v.setLat(TravelCoord.getLat());
								v.setLon(TravelCoord.getLon());
								System.out.println("Après le move les coords demandées sont ("+TravelCoord.getLat()+","+TravelCoord.getLon()+") et les coord réelles sont : ("+v.getLat()+","+v.getLon()+")");
								v.setProgress(v.getProgress()+v.getDl()/v.getD());
								vRepo.save(v);
								System.out.println("Finalement, après le save du thread, les coord réelles sont : ("+v.getLat()+","+v.getLon()+")");

							
							
						
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
