package com.fireforce.exting.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fireforce.exting.serv.extingServ;

@RestController
public class extingCtr {

	@Autowired
	extingServ eServ;
	
}
