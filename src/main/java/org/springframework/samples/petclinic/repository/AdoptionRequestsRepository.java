package org.springframework.samples.petclinic.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;

public interface AdoptionRequestsRepository extends CrudRepository<Adoption, Integer> {

	@Query("select a from Adoption a where a.pet.owner.id =:id")
	List<Adoption> getRequestsByUser(@Param("id") Integer id);

	
	@Query("select a from Adoption a where a.pet.id =:id")
	List<Adoption> findRequestsByPet(Integer id);

	@Query("select a from Adoption a where a.owner.id =:id")
	List<Adoption> getRequestsByUserApplicant(@Param("id") Integer id);
	
}
