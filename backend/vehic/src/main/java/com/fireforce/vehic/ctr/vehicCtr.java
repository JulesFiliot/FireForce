package com.fireforce.vehic.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fireforce.vehic.model.vehic;
import com.fireforce.vehic.serv.vehicServ;
import com.project.model.dto.Coord;
import com.project.model.dto.VehicleDto;

@RestController
public class vehicCtr {

	@Autowired
	vehicServ vServ;
	
	@RequestMapping(method = RequestMethod.POST, value = "/vehicle")
	public void addVehic(@RequestBody vehic v) {
		vServ.addVehic(v);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/vehicle")
	public void updateVehic(@RequestBody vehic v) {
		vServ.updateVehic(v);
	}
	
	@RequestMapping(value = "/vehicle/reset")
	public void resetVehic() {
		vServ.resetVehic();
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/vehicMove/{id}")
	public void vehicMove(@RequestBody Coord c, @PathVariable Integer id) {
		vServ.moveVehic(c,id);
	}
	
	@RequestMapping(value = "/generateVehics")
	public void generateVehics() {
		vServ.generateVehics();
	}
	
	
	@RequestMapping(value = "/getVehic/{id}")
	public vehic getVehic(@PathVariable Integer id) {
		return vServ.getVehic(id);
	}
	
	
	@RequestMapping(value = "/editVehicCoord/{id}/{lat}/{lon}")
	public void newCoord(@PathVariable Integer id, @PathVariable double lat, @PathVariable double lon) {
		vehic v = vServ.getVehic(id);
		vServ.newCoord(v, lat, lon);
		updateVehic(v);
	}

	@RequestMapping(value = "/getDispo")
	public Integer getDispo() {
		return vServ.getDispo();
	}
	
	@RequestMapping(value = "/switchDispo/{id}")
	public void switchDispo(@PathVariable Integer id) {
		vServ.switchDispo(id);
		return ;
	}
	
	@RequestMapping(value = "/endMission/{id}")
	public void endMission(@PathVariable Integer id) {
		vServ.endMission(id);
		return ;
	}

	/*
	@RequestMapping(value = "/editVehicLiquidType/{id}/{type}")
	public void newLiquidType(@PathVariable Integer id ,@PathVariable String type) {
		vehic v = vServ.getVehic(id);
		vServ.newLiquidType(v, type);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editVehicEfficiency/{id}/{val}")
	public void newEfficiency(@PathVariable Integer id, @PathVariable float val) {
		vehic v = vServ.getVehic(id);
		vServ.newEfficiency(v, val);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editVehicLiquidQuantity/{id}/{val}")
	public void newLiquidQuantity(@PathVariable Integer id, @PathVariable float val) {
		vehic v = vServ.getVehic(id);
		vServ.newLiquidQuantity(v, val);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editVehicLiquidConsumption/{id}/{val}")
	public void newLiquidConsumption(@PathVariable Integer id, @PathVariable float val) {
		vehic v = vServ.getVehic(id);
		vServ.newLiquidConsumption(v, val);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editVehicFuel/{id}/{val}")
	public void newFuel(@PathVariable Integer id, @PathVariable float val) {
		vehic v = vServ.getVehic(id);
		vServ.newFuel(v, val);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editVehicFuelConsumption/{id}/{val}")
	public void newFuelConsumption(@PathVariable Integer id, @PathVariable float val) {
		vehic v = vServ.getVehic(id);
		vServ.newFuelConsumption(v, val);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editCrewMember/{id}/{val}")
	public void newCrewMember(@PathVariable Integer id, @PathVariable int val) {
		vehic v = vServ.getVehic(id);
		vServ.newCrewMember(v, val);
		updateVehic(v, id);
	}
	
	@RequestMapping(value = "/editCrewMemberCapacity/{id}/{val}")
	public void newCrewMemberCapacity(@PathVariable Integer id, @PathVariable int val) {
		vehic v = vServ.getVehic(id);
		vServ.newCrewMemberCapacity(v, val);
		updateVehic(v, id);
	}*/
}
