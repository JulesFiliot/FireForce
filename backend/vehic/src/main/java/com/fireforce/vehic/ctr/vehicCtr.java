package com.fireforce.vehic.ctr;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/generateVehics")
	public void generateVehics() {
		vServ.generateVehics();
	}
}