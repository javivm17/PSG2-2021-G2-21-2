package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdoptionApplicationsServiceTests {
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected AdoptionService adoptionService;
	
	@Autowired
	protected AdoptionRequestService adoptionRequestService;
	
	@Test
	@Transactional
	void shouldSaveBooking() {
		final Owner o = this.ownerService.findOwnerById(2);
		final Pet p = this.petService.findPetById(2);
		final Adoption request = new Adoption();
		request.setPet(p);
		request.setDescription("Esto es una prueba");
		
			
		final Integer cont1= this.adoptionRequestService.getRequests(o).size(); 
		this.adoptionService.saveRequest(request);
		final Integer cont2= this.adoptionRequestService.getRequests(o).size(); 
			
		assertTrue(cont1<cont2);
		}
	
}
