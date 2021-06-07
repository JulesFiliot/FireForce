package com.fireforce.fire.ctr;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.serv.fireServ;
import com.project.model.dto.FireDto;

@RestController
public class fireCtr {
	
	@Autowired
	fireServ fServ;


	@RequestMapping(method=RequestMethod.GET,value="/stopdisplay")
	public void stopDisplay() {
		fServ.stopDisplay();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getAllFire")
	public ArrayList<FireDto> getAllFire() {
    	ArrayList<FireDto> fList = fServ.getAllFire();
    	return fList;
    }
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/config/creation")
	public void configCreation(@RequestBody Object conf) {
		fServ.configCreation(conf);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/config/behavior")
	public void configBehavior(@RequestBody Object conf) {
		fServ.configBehavior(conf);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/fire/reset")
	public void fireReset() {
		fServ.fireReset();
	}
}

