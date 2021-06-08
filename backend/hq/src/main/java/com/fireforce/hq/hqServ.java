package com.fireforce.hq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fireforce.hq.hq;
import com.fireforce.hq.hqRepo;
import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

@Service
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
	
	
	public void affectVehicSimple() {
		
		String reqUrl = "http://127.0.0.1:8081/fire";
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> reqFireDto = restTemplate.getForEntity(reqUrl,FireDto[].class);
		FireDto[] fArray=reqFireDto.getBody();
		for (FireDto fD : fArray) {
			Integer fId = fD.getId();
			reqUrl = "http://127.0.0.1:8092/getLinkedVehic/"+fId;
			ResponseEntity<ArrayList> reqLV = restTemplate.getForEntity(reqUrl,ArrayList.class);
			ArrayList<Integer> LV = reqLV.getBody();
			//System.out.println(LV);
			if (LV.isEmpty()){
				
				// -> Dire au Simulator que tel vehicule est maintenant assigné à tel feu

				reqUrl = "http://127.0.0.1:8094/getDispo";
				ResponseEntity<Integer> reqVID = restTemplate.getForEntity(reqUrl, Integer.class);
				Integer vId = reqVID.getBody();
				System.out.println(vId);
				if (vId!=null) {
					//System.out.println("Oui Oui Oui");					
					reqUrl = "http://127.0.0.1:8092/addLinkedVehic/"+fId+"/"+vId;
					restTemplate.getForEntity(reqUrl, null);
					
				}
				//on a l'id du vehic, on veut deplacer le vehic aux coordonnées du feu concerné
				Coord c = new Coord();

				
				
				c.setLat(fD.getLat());
				c.setLon(fD.getLon());
						
				if(vId!=null) {
				String reqMov = "http://127.0.0.1:8094/vehicMove/"+vId;
				restTemplate.put(reqMov, c);
				}
				
				return;
				
			}
		}
		
		
	/*	for (fire f : this.fRepo.findAll()) {
			Integer id=f.getId();
			if (f.getLinkedVehic().isEmpty()) {
				String reqVehic = "http://127.0.0.1:8094/getDispo";
				ResponseEntity<Integer> reqVID = restTemplate.getForEntity(reqVehic, Integer.class);
				
				Integer vId = reqVID.getBody();
				//System.out.println("=========================="+vId.toString());
				// -> Dire au Simulator que tel vehicule est maintenant assigné à tel feu
				
				if(vId!=null) {
				
				fServ.addLinkedVehic(f,vId);


				//on a l'id du vehic, on veut deplacer le vehic aux coordonnées du feu concerné
				Coord c = new Coord();

				
				String reqUrl2 = "http://127.0.0.1:8081/fire";
				ResponseEntity<FireDto[]> reqFireDto2 = restTemplate.getForEntity(reqUrl2,FireDto[].class);
				FireDto[] fArray2=reqFireDto2.getBody();
				for (FireDto fD : fArray) {
					if (fD.getId()==id)  {
						c.setLat(fD.getLat());
						c.setLon(fD.getLon());
						break;
					}
						
					}						
				String reqMov = "http://127.0.0.1:8094/vehicMove/"+vId;
				restTemplate.put(reqMov, c);
				

				}
				
				
			}
		}*/
		
	}
	
	
	@Bean(initMethod="init")
	public void init() {
        hq hq1 = new hq();
        
        this.add(hq1);
	}

	public void affectOptiStraight() {
		String reqUrl = "http://127.0.0.1:8081/fire";
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> reqFireDto = restTemplate.getForEntity(reqUrl,FireDto[].class);
		FireDto[] fArray=reqFireDto.getBody();
		for (FireDto fD : fArray) {
			Integer fId = fD.getId();
			reqUrl = "http://127.0.0.1:8092/getLinkedVehic/"+fId;
			ResponseEntity<Integer[]> reqLV = restTemplate.getForEntity(reqUrl,Integer[].class);
			ArrayList<Integer> LV = new ArrayList<Integer>(Arrays.asList(reqLV.getBody()));
			//System.out.println(LV);
			if (LV.isEmpty()){
				
				// -> Dire au Simulator que tel vehicule est maintenant assigné à tel feu

				
				//Récupérer les vehic dispo avec un type optimal
				reqUrl = "http://127.0.0.1:8094/getOptiTypeVehic/"+fD.getType();
				ResponseEntity<VehicleDto[]> reqVID = restTemplate.getForEntity(reqUrl, VehicleDto[].class);
				ArrayList<VehicleDto> VDto = new ArrayList<VehicleDto>(Arrays.asList(reqVID.getBody()));
				Integer vId = null;
				if (VDto.isEmpty()) {
					//On récupère le vehic le plus proche parmi tous les vehic	
					reqUrl = "http://127.0.0.1:8094/getAllVehicDto";
					ResponseEntity<VehicleDto[]> reqClosest = restTemplate.getForEntity(reqUrl, VehicleDto[].class);
					ArrayList<VehicleDto> lvdto = new ArrayList<VehicleDto>(Arrays.asList(reqClosest.getBody()));
					vId=this.getClosestVehicFromArrayList(fD,lvdto);

				}
				
				else if (VDto.size()==1) {
					//on a qu'un vehicule avec un type optimal donc le choisit par défaut
					VehicleDto t =VDto.get(0);
					vId=t.getId();
				}
				
				else {
					//plusieurs vehic ont un type optimal donc on choisit le plus proche
					vId=this.getClosestVehicFromArrayList(fD, VDto);
				}
				
				
				System.out.println(vId);
				if (vId!=null) {
					//System.out.println("Oui Oui Oui");					
					reqUrl = "http://127.0.0.1:8092/addLinkedVehic/"+fId+"/"+vId;
					restTemplate.getForEntity(reqUrl, null);
					
				}
				//on a l'id du vehic, on veut deplacer le vehic aux coordonnées du feu concerné
				Coord c = new Coord();

				
				
				c.setLat(fD.getLat());
				c.setLon(fD.getLon());
						
				if(vId!=null) {
				//String reqMov = "http://127.0.0.1:8094/vehicMove/"+vId;
					String reqMov = "http://127.0.0.1:8094/askMove/"+vId;
					restTemplate.postForEntity(reqMov, c, null);
				}
				
				return;
				
			}
		}
	}

	private Integer getClosestVehicFromArrayList(FireDto fD, ArrayList<VehicleDto> lvdto) {
		// TODO Auto-generated method stub
		Integer vId = null;
		double distance=16000;
		double distanceTampon;
		double lat1;
		double lat2=fD.getLat();
		double lon1;
		double lon2=fD.getLon();
		
		for (VehicleDto t : lvdto) {
			lat1=t.getLat();
			lon1=t.getLon();
			
			distanceTampon = 6371*Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lon1)*Math.cos(lon2)*Math.cos(lon2-lon1));
			if (distanceTampon<distance) {
				distance=distanceTampon;
				vId=t.getId();
			}
		}
		return vId;
	
	}

	
	
}
