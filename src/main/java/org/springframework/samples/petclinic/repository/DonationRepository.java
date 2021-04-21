package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository extends Repository<Donation, Integer>{

	void save(Donation donation) throws DataAccessException;
	
	void delete(Donation donation) throws DataAccessException;
	
	List<Donation> findByCauseId(Integer causeId);
	
	void deleteById(int id) throws DataAccessException;
	
	Donation findById(int id) throws DataAccessException;
	
	//Owner findOwnerByDonation(Donation donation);
}
