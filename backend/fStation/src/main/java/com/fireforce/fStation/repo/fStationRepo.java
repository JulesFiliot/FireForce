package com.fireforce.fStation.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fireforce.fStation.model.fStation;

public interface fStationRepo extends CrudRepository<fStation, Integer>{

	public List<fStation> findByName(String name);
}
