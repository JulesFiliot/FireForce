package com.fireforce.vehic.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fireforce.vehic.repo.vehicRepo;

@Service
public class vehicServ {
	
	@Autowired
	vehicRepo vRepo;

}
