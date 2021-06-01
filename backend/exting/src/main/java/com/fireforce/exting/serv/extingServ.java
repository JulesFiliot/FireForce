package com.fireforce.exting.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fireforce.exting.repo.extingRepo;


@Service
public class extingServ {
	
	@Autowired
	extingRepo eRepo;
}
