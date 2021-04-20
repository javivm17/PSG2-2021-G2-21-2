package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.AdoptionApplications;
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
		Owner o = ownerService.findOwnerById(2);
		Pet p = petService.findPetById(2);
		AdoptionApplications request = new AdoptionApplications();
		request.setPet(p);
		request.setDescription("Esto es una prueba");
		
			
		Integer cont1= adoptionRequestService.getRequests(o).size(); 
		adoptionService.saveRequest(request);
		Integer cont2= adoptionRequestService.getRequests(o).size(); 
			
		assertThat(cont1<cont2);
		}
	
}
