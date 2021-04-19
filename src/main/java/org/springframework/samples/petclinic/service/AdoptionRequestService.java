package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRequestsRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;

@Service
public class AdoptionRequestService {
	private AdoptionRequestsRepository adoptionRequestsRepository;
	private PetService petService;
	
	@Autowired
	public AdoptionRequestService(AdoptionRequestsRepository adoptionRequestsRepository, PetService petService) {
		this.adoptionRequestsRepository = adoptionRequestsRepository;
		this.petService= petService;
	}


	public List<AdoptionApplications> getRequests(Owner owner) {
		return adoptionRequestsRepository.getRequestsByUser(owner.getId());
	}
	
	public List<AdoptionApplications> getRequestsSended (Integer id){
		return adoptionRequestsRepository.getRequestsByUserApplicant(id);
	}

	
	public Optional<AdoptionApplications>getRequestById(int id){
		return adoptionRequestsRepository.findById(id);

	}
	 
	@Transactional
	public void acceptRequest(AdoptionApplications request) throws DataAccessException, DuplicatedPetNameException {
		Pet p = request.getPet();
		p.setInAdoption(false);
		Owner o = request.getOwner();
		p.setOwner(o);
		
		petService.savePet(p);
		
		//Además debemos eliminar las requests asociadas a esa mascota
		List<AdoptionApplications>requestsRestantes = adoptionRequestsRepository.findRequestsByPet(p.getId());
		for(AdoptionApplications a : requestsRestantes) {
			adoptionRequestsRepository.delete(a);	
		}
	}

	@Transactional
	public void removeRequest(Integer id) {
		adoptionRequestsRepository.deleteById(id);
	}


	public List<AdoptionApplications> getRequestsByPet(int id) {
		return adoptionRequestsRepository.findRequestsByPet(id);
	}

}
