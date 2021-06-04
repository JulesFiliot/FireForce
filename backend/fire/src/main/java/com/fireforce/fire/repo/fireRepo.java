package com.fireforce.fire.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fireforce.fire.model.fire;

public interface fireRepo extends CrudRepository<fire, Integer>{

	//public List<fire> findByName(String name);
	
}
