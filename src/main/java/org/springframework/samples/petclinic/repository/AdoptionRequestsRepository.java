package org.springframework.samples.petclinic.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AdoptionApplications;

public interface AdoptionRequestsRepository extends CrudRepository<AdoptionApplications, Integer> {

	@Query("select a from AdoptionApplications a where a.pet.owner.id =:id")
	List<AdoptionApplications> getRequestsByUser(@Param("id") Integer id);

}
