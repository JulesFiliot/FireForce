package com.fireforce.inter.repo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.fireforce.inter.model.inter;
import java.util.Optional;

public interface interRepo extends CrudRepository<inter, Integer>{

	public List<inter> findByName(String name);
	
}