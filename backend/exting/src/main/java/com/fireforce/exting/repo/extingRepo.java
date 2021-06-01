package com.fireforce.exting.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fireforce.exting.model.exting;

public interface extingRepo extends CrudRepository<exting, Integer>{

	public List<exting> findByName(String name);
	
}