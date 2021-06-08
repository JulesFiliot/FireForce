package com.fireforce.vehic.serv;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fireforce.vehic.model.vehic;
import com.fireforce.vehic.repo.vehicRepo;
import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;
import com.project.model.dto.FireType;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

@Service
public class vehicServ {
	
	@Autowired
	vehicRepo vRepo;
 
	
	vehicThread vThread;
	private Thread DisplayThread;
	
	public vehicServ(vehicRepo vRepo) {
		//Replace the @Autowire annotation....
		this.vRepo=vRepo;
		
		//Create a Runnable is charge of executing cyclic actions 
		this.vThread=new vehicThread(this.vRepo,this);
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		DisplayThread=new Thread(vThread);
		System.out.println("le thread va démarrer");
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		DisplayThread.start();
	}
	
	public void stopDisplay() {
		//Call the user defined stop method of the runnable
		this.vThread.stop();
		try {
			//force the thread to stop
			this.DisplayThread.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addVehic(vehic v) {
		VehicleDto t = new VehicleDto(0,v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());
    	
		String reqUrl = "http://127.0.0.1:8081/vehicle";
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<VehicleDto> reqVehicDTO = restTemplate.postForEntity(reqUrl, t,VehicleDto.class);
		VehicleDto xv = reqVehicDTO.getBody();
		
		v.setRemoteId(xv.getId());
		vehic createdVehic = vRepo.save(v);
		
		System.out.println(createdVehic);
	}

	public void generateVehics() {
		vehic v1 = new vehic(0,0, VehicleType.PUMPER_TRUCK, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);
		vehic v2 = new vehic(0,0, VehicleType.FIRE_ENGINE, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);
		vehic v3 = new vehic(0,0, VehicleType.CAR, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);
		vehic v4 = new vehic(0,0, VehicleType.WATER_TENDERS, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);

		this.addVehic(v1);
		this.addVehic(v2);
		this.addVehic(v3);
		this.addVehic(v4);
	}
	
	public void updateVehic(vehic v) {
		VehicleDto t = new VehicleDto(v.getRemoteId(),v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());
    	String reqUrl = "http://127.0.0.1:8081/vehicle/"+v.getRemoteId();
        RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(reqUrl, t);
		
		vehic newV = getVehic(v.getId());
		newV = v;
		vRepo.save(newV);
		
		System.out.println("à la sortie du save de update v est :"+newV.toString());
	}
	
	public vehic getVehic(Integer id) {
		Optional<vehic> vOpt = vRepo.findById(id);
		if (vOpt.isPresent()) return vOpt.get();
		else return null;
	}
	
	public void newCoord(vehic v, double lat, double lon) {
		v.setLat(lat);
		v.setLon(lon);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newLiquidType(vehic v, String type) {
		if (type.equals("A")) v.setLiquidType(LiquidType.ALL);
		else if (type.equals("W")) v.setLiquidType(LiquidType.WATER);
		else if (type.equals("WA")) v.setLiquidType(LiquidType.WATER_WITH_ADDITIVES);
		else if (type.equals("C")) v.setLiquidType(LiquidType.CARBON_DIOXIDE);
		else if (type.equals("P")) v.setLiquidType(LiquidType.POWDER);
		else {
			System.out.println("Error : unknown liquid type.");
			return;
		}
		vRepo.save(v);
		System.out.println(v);
	}

	public void newEfficiency(vehic v, float val) {
		v.setEfficiency(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newLiquidQuantity(vehic v, float val) {
		v.setLiquidQuantity(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newLiquidConsumption(vehic v, float val) {
		v.setLiquidConsumption(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newFuel(vehic v, float val) {
		v.setFuel(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newFuelConsumption(vehic v, float val) {
		v.setFuelConsumption(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newCrewMember(vehic v, int val) {
		v.setCrewMember(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void newCrewMemberCapacity(vehic v, int val) {
		v.setCrewMemberCapacity(val);
		vRepo.save(v);
		System.out.println(v);
	}

	public void moveVehic(Coord c, Integer id) {
		vehic v = getVehic(id);

		System.out.println("on bouge vehic "+id+" vers coord"+c.getLat()+","+c.getLon());
		
		v.setLat(c.getLat());
		v.setLon(c.getLon());
		updateVehic(v);
		
		System.out.println(v);
	}

	public Integer getDispo() {
		System.out.println(this.vRepo.findAll());
		for (vehic v : this.vRepo.findAll()) {
			//System.out.println(v+" <=> "+v.isDispo());
			if (v.isDispo()) {
				System.out.println("Vehic "+v.getId() +" est dispo (out getDispo)");
				return v.getId();
				}
			
		}
		return null;
	}

	public void switchDispo(Integer id) {
		vehic v = getVehic(id);
		if (v!=null) {
		v.setDispo(!getVehic(id).isDispo());
		vRepo.save(v);
		System.out.println("Vehic "+v.getId()+" -> switchDispo, is dispo now?"+v.isDispo());}
	}

	public void endMission(Integer id) {
		Integer fSId = getVehic(id).getFacilityRefID();
		
		String reqUrl = "http://127.0.0.1:8098/getFSCoord/"+fSId;
        RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<Coord> reqCoord = restTemplate.getForEntity(reqUrl, Coord.class);
		Coord cFS = reqCoord.getBody();
		Coord c = new Coord(cFS.getLon(),cFS.getLat());
		this.switchDispo(id);
	//	this.moveVehic(c, id);	
		this.askMoveVehic(c, getVehic(id).getRemoteId());
		System.out.println("Fin mission véhicule"+id);
	}

	public void resetVehic() {
		String reqUrl = "http://127.0.0.1:8081/vehicle";
        RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<VehicleDto[]> reqVehic = restTemplate.getForEntity(reqUrl, VehicleDto[].class);
    	VehicleDto[] SimuVehics = reqVehic.getBody();
    	
    	for(VehicleDto vD : SimuVehics) {
    		Integer rId = vD.getId();
    		String reqUrl2 = "http://127.0.0.1:8081/vehicle/"+rId;
	    	restTemplate.delete(reqUrl2);;
    		
    	}
		vRepo.deleteAll();

    	
    	
		// TODO Auto-generated method stub
		
	}
	


	/*@Bean(initMethod="init")
	public void init() {
		vehic v1 = new vehic(0,0, VehicleType.PUMPER_TRUCK, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);
		vehic v2 = new vehic(0,0, VehicleType.FIRE_ENGINE, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);
		vehic v3 = new vehic(0,0, VehicleType.CAR, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);
		vehic v4 = new vehic(0,0, VehicleType.WATER_TENDERS, 10, LiquidType.ALL, 1000, 1, 1, 1, 4, 4, 1);

		this.addVehic(v1);
		this.addVehic(v2);
		this.addVehic(v3);
		this.addVehic(v4);
	}
	*/

	public ArrayList<vehic> getAllVehic() {
		ArrayList<vehic> ListVehic = new ArrayList<vehic>();
		Iterable<vehic> allVehic = vRepo.findAll();
		Iterator<vehic> iterator = allVehic.iterator();
		while(iterator.hasNext()) {
		    vehic it = iterator.next();
		    ListVehic.add(it);
		}
		return ListVehic;
	}

	public ArrayList<vehic> getAllDisp() {
		ArrayList<vehic> ListVehic = new ArrayList<vehic>();
		Iterable<vehic> allVehic = vRepo.findAll();
		Iterator<vehic> iterator = allVehic.iterator();
		while(iterator.hasNext()) {
		    vehic it = iterator.next();
		    if (it.isDispo()) {
		    	ListVehic.add(it);
		    }
		}
		return ListVehic;
	}

	
	public void delVehic(Integer id) {
		vehic v =getVehic(id);
		String reqUrl = "http://127.0.0.1:8081/vehicle/" + v.getRemoteId().toString();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(reqUrl);
		vRepo.delete(v);
	}

	public ArrayList<VehicleDto> getOptiTypeVehic(FireType type) {
		ArrayList<VehicleDto> ListVDto = new ArrayList<VehicleDto>();
		ArrayList<vehic> ListVehic = this.getAllDisp();
		for (vehic v: ListVehic) {
			if (v.getLiquidType().getEfficiency(type.toString())>=0.8) {
				VehicleDto t = new VehicleDto(v.getRemoteId(),v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());
				ListVDto.add(t);
			}
		}
		return ListVDto;
	
	}

	public ArrayList<VehicleDto> getAllDispDto() {
		ArrayList<VehicleDto> ListVehicDto = new ArrayList<VehicleDto>();
		Iterable<vehic> allVehic = vRepo.findAll();
		Iterator<vehic> iterator = allVehic.iterator();
		while(iterator.hasNext()) {
		    vehic v = iterator.next();
		    VehicleDto t = new VehicleDto(v.getRemoteId(),v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());
		    if (v.isDispo()) {
		    	ListVehicDto.add(t);
		    }
		}
		return ListVehicDto;
	}
	
	public ArrayList<VehicleDto> getAllVehicDto() {
		ArrayList<VehicleDto> ListVehicDto = new ArrayList<VehicleDto>();
		Iterable<vehic> allVehic = vRepo.findAll();
		Iterator<vehic> iterator = allVehic.iterator();
		while(iterator.hasNext()) {
		    vehic v = iterator.next();
		    VehicleDto t = new VehicleDto(v.getRemoteId(),v.getLon(),v.getLat(),v.getType(),v.getEfficiency(),v.getLiquidType(),v.getLiquidQuantity(),v.getLiquidConsumption(),v.getFuel(),v.getFuelConsumption(),v.getCrewMember(),v.getCrewMemberCapacity(),v.getFacilityRefID().intValue());

		    ListVehicDto.add(t);
		}
		return ListVehicDto;
		
	}

	public void askMoveVehic(Coord c, Integer id) { //utilise le remoteId
		//on update mvRepo avec une nouvelle demande de déplacement
		System.out.println("askMoveVehic :le remote id utilisé ici :"+id);
		//Optional<vehic> vOpt = vRepo.findById(id);
		Optional<vehic> vOpt = vRepo.findByRemoteId(id);
		
		System.out.println("askMoveVehic :vOpt:"+vOpt.toString());
		System.out.println("askMoveVehic :vOpt isPresent ?"+vOpt.isPresent());
		if (vOpt.isPresent()) {
			vehic v=vOpt.get();
			v.setEndPoint(c);
			
			Coord StPt = new Coord(v.getLon(),v.getLat());
			v.setStartPoint(StPt);
			//v.computeDeltaLat(v.getEndPoint(), v.getStartPoint());
			//v.computeDeltaLon(v.getEndPoint(), v.getStartPoint());
			v.computeAll(v.getEndPoint(),v.getStartPoint());
			v.setMoving(true);
			vRepo.save(v);
			System.out.println("askMoveVehic : is V (remoteId "+id+ " ) moving " +v.isMoving());
		}
		
	}

	public Integer getRepoId(Integer remoteId) {
		return vRepo.findByRemoteId(remoteId).get().getId();
	}

}
