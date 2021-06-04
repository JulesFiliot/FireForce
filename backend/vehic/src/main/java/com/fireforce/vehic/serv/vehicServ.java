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
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleDto;
import com.project.model.dto.VehicleType;

@Service
public class vehicServ {
	
	@Autowired
	private final vehicRepo vRepo;

	public vehicServ(vehicRepo vRepo) {
		this.vRepo = vRepo;
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

		System.out.println(c);
		
		v.setLat(c.getLat());
		v.setLon(c.getLon());
		updateVehic(v);
		
		System.out.println(v);
	}

	public Integer getDispo() {
		System.out.println(this.vRepo.findAll());
		for (vehic v : this.vRepo.findAll()) {
			System.out.println(v+" <=> "+v.isDispo());
			if (v.isDispo()) {
				System.out.println("====vGetId getDispo===="+v.getId());
				return v.getId();
				}
			
		}
		return null;
	}

	public void switchDispo(Integer id) {
		vehic v = getVehic(id);
		v.setDispo(!getVehic(id).isDispo());
		vRepo.save(v);
	}

	public void endMission(Integer id) {
		Integer fSId = getVehic(id).getFacilityRefID();
		
		String reqUrl = "http://127.0.0.1:8098/getFSCoord/"+fSId;
        RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<Coord> reqCoord = restTemplate.getForEntity(reqUrl, Coord.class);
		Coord cFS = reqCoord.getBody();
		Coord c = new Coord(cFS.getLon(),cFS.getLat());
		this.switchDispo(id);
		this.moveVehic(c, id);		
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
	}*/

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
	


}
