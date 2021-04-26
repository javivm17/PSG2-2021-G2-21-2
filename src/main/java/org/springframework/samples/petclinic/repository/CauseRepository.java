package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer> {

	@Query("SELECT c FROM Cause c ORDER BY c.id")
	List<Cause> findAll() throws DataAccessException;
	
	Cause findById(int id) throws DataAccessException;
	
	void save(Cause c) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException; 
	
	@Query("SELECT SUM(d.amount) FROM Donation d WHERE d.cause.id = :causeId")
	Integer getCurrentBudget(@Param("causeId") int causeId) throws DataAccessException;
}
