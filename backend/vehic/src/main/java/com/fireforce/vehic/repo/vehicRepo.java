package com.fireforce.vehic.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fireforce.vehic.model.vehic;

@Repository
public interface vehicRepo extends CrudRepository<vehic, Integer>{


	public Optional<vehic> findByRemoteId(Integer id);
	
}
