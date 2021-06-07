package com.fireforce.fire.serv;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.repo.fireRepo;
import com.project.model.dto.FireType;
import com.project.model.dto.FireDto;


@Service
public class fireServ {
	
	@Autowired
	fireRepo fRepo;
	
	DisplayRunnable dRunnable;
	private Thread displayThread;
	
	public fireServ(fireRepo fRepo) {
		//Replace the @Autowire annotation....
		this.fRepo=fRepo;
		
		//Create a Runnable is charge of executing cyclic actions 
		this.dRunnable=new DisplayRunnable(this.fRepo,this);
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		displayThread=new Thread(dRunnable);
		System.out.println("le thread va démarrer");
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		displayThread.start();
		
	}
	
	public void stopDisplay() {
		//Call the user defined stop method of the runnable
		this.dRunnable.stop();
		try {
			//force the thread to stop
			this.displayThread.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
/*	@Bean(initMethod="init")
	public void init() {
	//	fire f1=new fire();
//		fire f2=new fire();
		fRepo.save(f1);
		fRepo.save(f2);
	}*/




	public void addFireA(fire f) {  	
		fire createdFireA = fRepo.save(f);
		System.out.println(createdFireA);

	}

	public void addLinkedVehic(fire f,Integer vId) {
		f.addLinkedVehic(vId);
		fRepo.save(f);		
		
		String reqVehic = "http://127.0.0.1:8094/switchDispo/"+vId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity(reqVehic, Integer.class);
		
	}

	public ArrayList<FireDto> getAllFire() {
		ArrayList<FireDto> ListFire = new ArrayList<FireDto>();
		
		String reqUrl = "http://127.0.0.1:8081/fire";
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> reqFireDto = restTemplate.getForEntity(reqUrl,FireDto[].class);
		FireDto[] fArray = reqFireDto.getBody();
		
		for (FireDto fD : fArray) {
			ListFire.add(fD);
		}
		
		if (ListFire.isEmpty()) return null;
		
		return ListFire;
	}

	public ArrayList<Integer> getLinkedVehic(Integer id) {
		Optional<fire> fOpt = fRepo.findById(id);
		if (fOpt.isPresent()) {
			fire f=fOpt.get();
			return f.getLinkedVehic();
		}
		return null;
	}
	
	public void addLinkedVehic(Integer fId, Integer vId) {
		Optional<fire> fOpt =fRepo.findById(fId);
		if (fOpt.isPresent()) {
			fire f = fOpt.get();
			f.addLinkedVehic(vId);
			fRepo.save(f);		
			
			String reqVehic = "http://127.0.0.1:8094/switchDispo/"+vId;
			System.out.println("Le véhicule "+vId+" a été associé au feu "+fId);
	        RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getForEntity(reqVehic, Integer.class);
		}
		
	}

	public void configCreation(Object conf) {
		String reqUrl = "http://127.0.0.1:8081/config/creation";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(reqUrl, conf);
	}

	public void configBehavior(Object conf) {
		String reqUrl = "http://127.0.0.1:8081/config/behavior";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(reqUrl, conf);
	}

	public void fireReset() {
		String reqUrl = "http://127.0.0.1:8081/fire/reset";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForEntity(reqUrl, Object.class);
	}
}
