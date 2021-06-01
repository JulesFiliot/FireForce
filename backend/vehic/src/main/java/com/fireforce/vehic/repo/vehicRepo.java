package com.fireforce.vehic.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fireforce.vehic.model.vehic;


public interface vehicRepo extends CrudRepository<vehic, Integer>{

	public List<vehic> findByName(String name);
	
}
