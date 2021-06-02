package com.fireforce.inter.serv;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import com.fireforce.inter.model.inter;
import com.fireforce.inter.repo.interRepo;

import java.util.ArrayList;
import java.util.Iterator;



import java.util.Optional;


@Service
public class interServ {

	private final interRepo iRepo;
	
	public interServ(interRepo iRepo) {
		this.iRepo = iRepo;
	}
	
}