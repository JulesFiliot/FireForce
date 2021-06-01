package com.fireforce.fire.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fireforce.fire.repo.fireRepo;


@Service
public class fireServ {
	
	@Autowired
	fireRepo fRepo;
}
