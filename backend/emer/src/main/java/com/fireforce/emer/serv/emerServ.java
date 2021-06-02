package com.fireforce.emer.serv;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import com.fireforce.emer.model.emer;
import com.fireforce.emer.repo.emerRepo;

import java.util.ArrayList;
import java.util.Iterator;



import java.util.Optional;


@Service
public class emerServ {

	private final emerRepo eRepo;
	
	public emerServ(emerRepo eRepo) {
		this.eRepo = eRepo;
	}
	
}