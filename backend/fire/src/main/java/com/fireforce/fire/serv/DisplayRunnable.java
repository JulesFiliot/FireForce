package com.fireforce.fire.serv;

import java.util.Iterator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.repo.fireRepo;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

public class DisplayRunnable implements Runnable {

	private fireServ fServ;
	private fireRepo fRepo;
	boolean isEnd = false;

	public DisplayRunnable(fireRepo fRepo) {
		this.fRepo = fRepo;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(10000);
				String reqUrl = "http://127.0.0.1:8081/fire";
		        RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<FireDto[]> reqFireDto = restTemplate.getForEntity(reqUrl,FireDto[].class);
				FireDto[] fArray=reqFireDto.getBody();
				for (FireDto fD : fArray) {
					if (fRepo.findById(fD.getId()) == null){
						fire f = new fire();
						f.setId(fD.getId());
						fServ.addFireA(f);
					}					
				}
				
				for (fire f : this.fRepo.findAll()) {
					Integer id=f.getId();
					boolean present=false;
					for (FireDto fD : fArray) {
						if (fD.getId()==id)  {
							present=true;
							break;
						}
						
					}
					if (!present) {
						System.out.println("Feu supprimé:"+fRepo.findById(id));
						fRepo.deleteById(id);
					}
						
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Runnable DisplayRunnable ends.... ");
	}

	public void stop() {
		this.isEnd = true;
	}

}
