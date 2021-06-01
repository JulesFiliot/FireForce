package com.fireforce.vehic.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fireforce.vehic.serv.vehicServ;

@RestController
public class vehicCtr {

	@Autowired
	vehicServ vServ;
}
