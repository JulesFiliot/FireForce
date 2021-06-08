package com.fireforce.fire.serv;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.repo.fireRepo;
import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

public class DisplayRunnable implements Runnable {

	
	private fireServ fServ;
	private fireRepo fRepo;
	boolean isEnd = false;

	public DisplayRunnable(fireRepo fRepo,fireServ fServ) {
		this.fRepo = fRepo;
		this.fServ = fServ;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				//System.out.println("On est DANS le thread");

				Thread.sleep(1000);
				String reqUrl = "http://127.0.0.1:8081/fire";
		        RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<FireDto[]> reqFireDto = restTemplate.getForEntity(reqUrl,FireDto[].class);
				FireDto[] fArray=reqFireDto.getBody();
				for (FireDto fD : fArray) {
					//System.out.println(fD.getId());
					//System.out.println(fRepo.findById(fD.getId()));
					//System.out.println(fRepo.findById(fD.getId()) == null);
					if (fRepo.findById(fD.getId()).isEmpty()){
						System.out.println("on veut ajouter feu "+fD.getId());
						fire f = new fire();
						f.setId(fD.getId());
						//System.out.println(fServ);
						fServ.addFireA(f);
					}					
				}
				
				//System.out.println(this.fRepo.findAll());
				//System.out.println(fArray);
				for (fire f : this.fRepo.findAll()) {
					Integer id=f.getId();
					boolean present=false;
					for (FireDto fD : fArray) {
						//System.out.println("fd get id"+fD.getId()+"et id "+id);
						if (fD.getId().equals(id))  {
							System.out.println(id+" est bien présent dans sim ET repo");
							present=true;
							break;
						}
						
					}
					if (!present) {
						System.out.println("Feu supprimé:"+fRepo.findById(id));
						for (Integer vId :f.getLinkedVehic()){
							if (vId != null) {
								System.out.println("requete endMission sur le vehic "+vId);
							String reqEM = "http://127.0.0.1:8094/endMission/"+vId;
							restTemplate.getForEntity(reqEM,null);
							}
						}
						fRepo.deleteById(id);
						
					}
						
					
				}
				
				
				// Maintenant on assigne les véhicules là où il faut
				
			//	String reqVehic = "http://127.0.0.1:8095/affectVehicSimple";
				String reqVehic = "http://127.0.0.1:8095/affectOptiStraight";
				restTemplate.getForEntity(reqVehic, null);
				
			/*	for (fire f : this.fRepo.findAll()) {
					Integer id=f.getId();
					if (f.getLinkedVehic().isEmpty()) {
						String reqVehic = "http://127.0.0.1:8094/getDispo";
						ResponseEntity<Integer> reqVID = restTemplate.getForEntity(reqVehic, Integer.class);
						
						Integer vId = reqVID.getBody();
						System.out.println("=========================="+vId.toString());
						// -> Dire au Simulator que tel vehicule est maintenant assigné à tel feu
						
						if(vId!=null) {
						
						fServ.addLinkedVehic(f,vId);


						//on a l'id du vehic, on veut deplacer le vehic aux coordonnées du feu concerné
						Coord c = new Coord();

						
						String reqUrl2 = "http://127.0.0.1:8081/fire";
						ResponseEntity<FireDto[]> reqFireDto2 = restTemplate.getForEntity(reqUrl2,FireDto[].class);
						FireDto[] fArray2=reqFireDto2.getBody();
						System.out.println("test fArray"+fArray.toString());
						for (FireDto fD : fArray) {
							System.out.println("fDgetId"+fD.getId()+"et id"+id);
							if (fD.getId().equals(id))  {
								c.setLat(fD.getLat());
								c.setLon(fD.getLon());
								System.out.println("nouvelles coord"+c.getLat()+","+c.getLon());
								break;
							}
								
							}						
						String reqMov = "http://127.0.0.1:8094/vehicMove/"+vId;
						restTemplate.put(reqMov, c);
						

						}
						
						
					}
				}*/
				
				
				System.out.println("dans le repo"+this.fRepo.findAll().toString());
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
