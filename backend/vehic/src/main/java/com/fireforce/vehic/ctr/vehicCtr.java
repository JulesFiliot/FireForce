package com.fireforce.vehic.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fireforce.vehic.model.vehic;
import com.fireforce.vehic.serv.vehicServ;

@RestController
public class vehicCtr {

	@Autowired
	vehicServ vServ;
	
	@RequestMapping(method = RequestMethod.POST,value = "/addVehic")
	public void addVehic(@RequestBody vehic v) {
		vServ.addVehic(v);
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
		vServ.newCoord(vServ.getVehic(id), lat, lon);
	}
	
	@RequestMapping(value = "/editVehicLiquidType/{id}/{type}")
	public void newLiquidType(@PathVariable Integer id ,@PathVariable String type) {
		vServ.newLiquidType(vServ.getVehic(id), type);
	}
	
	@RequestMapping(value = "/editVehicEfficiency/{id}/{val}")
	public void newEfficiency(@PathVariable Integer id, @PathVariable float val) {
		vServ.newEfficiency(vServ.getVehic(id), val);
	}
	
	@RequestMapping(value = "/editVehicLiquidQuantity/{id}/{val}")
	public void newLiquidQuantity(@PathVariable Integer id, @PathVariable float val) {
		vServ.newLiquidQuantity(vServ.getVehic(id), val);
	}
	
	@RequestMapping(value = "/editVehicLiquidConsumption/{id}/{val}")
	public void newLiquidConsumption(@PathVariable Integer id, @PathVariable float val) {
		vServ.newLiquidConsumption(vServ.getVehic(id), val);
	}
	
	@RequestMapping(value = "/editVehicFuel/{id}/{val}")
	public void newFuel(@PathVariable Integer id, @PathVariable float val) {
		vServ.newFuel(vServ.getVehic(id), val);
	}
	
	@RequestMapping(value = "/editVehicFuelConsumption/{id}/{val}")
	public void newFuelConsumption(@PathVariable Integer id, @PathVariable float val) {
		vServ.newFuelConsumption(vServ.getVehic(id), val);
	}
	
	@RequestMapping(value = "/editCrewMember/{id}/{val}")
	public void newCrewMember(@PathVariable Integer id, @PathVariable int val) {
		vServ.newCrewMember(vServ.getVehic(id), val);
	}
	
	@RequestMapping(value = "/editCrewMemberCapacity/{id}/{val}")
	public void newCrewMemberCapacity(@PathVariable Integer id, @PathVariable int val) {
		vServ.newCrewMemberCapacity(vServ.getVehic(id), val);
	}
}
