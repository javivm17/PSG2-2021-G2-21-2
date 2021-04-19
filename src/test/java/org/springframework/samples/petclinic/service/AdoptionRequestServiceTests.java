package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdoptionRequestServiceTests {
		
	@Autowired
	private PetService petService;
	
	@Autowired
	private AdoptionRequestService adoptionRequestService;
	
	@Test
	void shouldGetRequestById() {
		AdoptionApplications a = adoptionRequestService.getRequestById(1).get();
		assertNotNull(a);
	}
	
	@Test
	void shouldAcceptRequest() throws DataAccessException, DuplicatedPetNameException {
		AdoptionApplications a = adoptionRequestService.getRequestById(1).get();
		Integer idOwnerBeforeAdopt = a.getPet().getOwner().getId();		
		
		adoptionRequestService.acceptRequest(a);
		Integer idOwnerAfterAdopt = petService.findPetById(a.getPet().getId()).getOwner().getId();

		assertNull(adoptionRequestService.getRequestById(1).orElse(null));
		assertNotEquals(idOwnerAfterAdopt,idOwnerBeforeAdopt);//We check that the owner has changed		
	}
	
	@Test 
	void shouldDeleteRequest(){
		AdoptionApplications a = adoptionRequestService.getRequestById(1).get();
		
		adoptionRequestService.removeRequest(1);
		
		assertNotNull(a);
		assertNull(adoptionRequestService.getRequestById(1).orElse(null));
	}
	
	@Test
	void shouldGetRequestsByPet() {
		List<AdoptionApplications> ls = adoptionRequestService.getRequestsByPet(1);
		assertNotNull(ls);	
	}
	
}
