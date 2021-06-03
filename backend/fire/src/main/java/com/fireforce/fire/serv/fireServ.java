package com.fireforce.fire.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.repo.fireRepo;
import com.project.model.dto.FireDto;


@Service
public class fireServ {
	
	@Autowired
	fireRepo fRepo;

	public void addFire() {  	
		String reqUrl = "http://127.0.0.1:8081/fire";
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> reqFireDTO = restTemplate.getForEntity(reqUrl, Object[].class);
		Object[] liste = reqFireDTO.getBody();
		System.out.println(liste);		
	}
}
