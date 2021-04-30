package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;

public interface DonationRepository extends Repository<Donation, Integer>{

	void save(Donation donation) throws DataAccessException;
	
	void delete(Donation donation) throws DataAccessException;
	
	List<Donation> findByCauseId(Integer causeId);
	
	void deleteById(int id) throws DataAccessException;
	
	Donation findById(int id) throws DataAccessException;
	
	@Query("SELECT o FROM Owner o ORDER BY o.id")
	List<Owner> findOwners();
	
	@Query("SELECT o FROM Owner o WHERE o.user.username = :username")
	Owner findOwnerByUsername(@Param ("username") String username);
}
