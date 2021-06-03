package com.fireforce.fire.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fireforce.fire.serv.fireServ;

@RestController
public class fireCtr {
	
	@Autowired
	fireServ fServ;
	
	@RequestMapping(method=RequestMethod.GET,value="/stopdisplay")
	public void stopDisplay() {
		fServ.stopDisplay();
	}

}
