package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.AdoptionRequestsRepository;
import org.springframework.stereotype.Service;

@Service
public class AdoptionRequestService {
	private AdoptionRequestsRepository adoptionRequestsRepository;
	
	@Autowired
	public AdoptionRequestService(AdoptionRequestsRepository adoptionRequestsRepository) {
		this.adoptionRequestsRepository = adoptionRequestsRepository;
	}


	public List<AdoptionApplications> getRequests(Owner owner) {
		return adoptionRequestsRepository.getRequestsByUser(owner.getId());
	}

}
