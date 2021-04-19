package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.AdoptionApplications;

public interface AdoptionRepository extends Repository<AdoptionApplications, Integer>{
	
	void save(AdoptionApplications request) throws DataAccessException;
	
	
}
