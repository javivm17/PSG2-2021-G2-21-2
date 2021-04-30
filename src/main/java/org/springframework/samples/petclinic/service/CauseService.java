package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private final CauseRepository causeRepo;
	
	@Autowired
	public CauseService(final CauseRepository causeRepo) {
		this.causeRepo = causeRepo;
	}
	
	@Transactional
	public List<Cause> findAll() {
		return causeRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Cause findCauseById(int id){ 
		return causeRepo.findById(id);
	}
	
	@Transactional
	public  void save(Cause cause) {
		causeRepo.save(cause);
	}

	public void delete(int id) { 
		causeRepo.deleteById(id);
	}
	
}
