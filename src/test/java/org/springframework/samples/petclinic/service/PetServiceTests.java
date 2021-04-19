/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;



import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.AdoptionRequestsRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PetServiceTests {        
        @Autowired
        protected PetService petService;
        
        @Autowired
        private  PetRepository petRepository;
        
        @Autowired
        protected OwnerService ownerService;	
        
        @Autowired
        protected AdoptionRequestsRepository adoptionRequestsRepository;

	@Test
	void shouldFindPetWithCorrectId() {
		final Pet pet7 = this.petService.findPetById(7);
		assertThat(pet7.getName()).startsWith("Samantha");
		assertThat(pet7.getOwner().getFirstName()).isEqualTo("Jean");

	}

	@Test
	void shouldFindAllPetTypes() {
		final Collection<PetType> petTypes = this.petService.findPetTypes();

		final PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
		assertThat(petType1.getName()).isEqualTo("gato");
		final PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
		assertThat(petType4.getName()).isEqualTo("serpiente");
	}

	@Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		final int found = owner6.getPets().size();

		final Pet pet = new Pet();
		pet.setName("bowser");
		final Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);

            try {
                this.petService.savePet(pet);
            } catch (final DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }
		this.ownerService.saveOwner(owner6);

		owner6 = this.ownerService.findOwnerById(6);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(pet.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingPetsWithTheSameName() {
		final Owner owner6 = this.ownerService.findOwnerById(6);
		final Pet pet = new Pet();
		pet.setName("wario");
		final Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		try {
			this.petService.savePet(pet);		
		} catch (final DuplicatedPetNameException e) {
			// The pet already exists!
			e.printStackTrace();
		}
		
		final Pet anotherPetWithTheSameName = new Pet();		
		anotherPetWithTheSameName.setName("wario");
		anotherPetWithTheSameName.setType(EntityUtils.getById(types, PetType.class, 1));
		anotherPetWithTheSameName.setBirthDate(LocalDate.now().minusWeeks(2));
		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
			owner6.addPet(anotherPetWithTheSameName);
			this.petService.savePet(anotherPetWithTheSameName);
		});		
	}

	@Test
	@Transactional
	public void shouldUpdatePetName() throws Exception {
		Pet pet7 = this.petService.findPetById(7);
		final String oldName = pet7.getName();

		final String newName = oldName + "X";
		pet7.setName(newName);
		this.petService.savePet(pet7);

		pet7 = this.petService.findPetById(7);
		assertThat(pet7.getName()).isEqualTo(newName);
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionUpdatingPetsWithTheSameName() {
		final Owner owner6 = this.ownerService.findOwnerById(6);
		final Pet pet = new Pet();
		pet.setName("wario");
		final Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		
		final Pet anotherPet = new Pet();		
		anotherPet.setName("waluigi");
		anotherPet.setType(EntityUtils.getById(types, PetType.class, 1));
		anotherPet.setBirthDate(LocalDate.now().minusWeeks(2));
		owner6.addPet(anotherPet);
		
		try {
			this.petService.savePet(pet);
			this.petService.savePet(anotherPet);
		} catch (final DuplicatedPetNameException e) {
			// The pets already exists!
			e.printStackTrace();
		}				
			
		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
			anotherPet.setName("wario");
			this.petService.savePet(anotherPet);
		});		
	}

	@Test
	@Transactional
	public void shouldAddNewVisitForPet() {
		Pet pet7 = this.petService.findPetById(7);
		final int found = pet7.getVisits().size();
		final Visit visit = new Visit();
		pet7.addVisit(visit);
		visit.setDescription("test");
		this.petService.saveVisit(visit);
            try {
                this.petService.savePet(pet7);
            } catch (final DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }

		pet7 = this.petService.findPetById(7);
		assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
		assertThat(visit.getId()).isNotNull();
	}

	@Test
	void shouldFindVisitsByPetId() throws Exception {
		final Collection<Visit> visits = this.petService.findVisitsByPetId(7);
		assertThat(visits.size()).isEqualTo(2);
		final Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
		assertThat(visitArr[0].getPet()).isNotNull();
		assertThat(visitArr[0].getDate()).isNotNull();
		assertThat(visitArr[0].getPet().getId()).isEqualTo(7);
	}
	
	@Test
    @Transactional
    void shouldDeletePetWithoutAdoptionrequests() {
        
        this.petService.deletePetById(1);

        final Pet petDel = this.petService.findPetById(1);
        assertThat(petDel).isNull();
       
    }
	
	@Test
    @Transactional
    void shouldDeletePetWithAdoptionrequests() {
        
        this.petService.deletePetById(2);

        Pet petDel = this.petService.findPetById(2);
        assertThat(petDel).isNull();
       
    }
	
	@Test
	@Transactional
	void shouldDeleteVisitByIdPetWithout() {
		final Owner owner = this.ownerService.findOwnerById(6);
		final Pet pet = owner.getPet("Max");
		final int found = pet.getVisits().size();
		
		this.petService.deleteVisit(2);
		
		
		final int visitDel = this.petService.findVisitsByPetId(8).size();
		assertThat(visitDel).isEqualTo(found - 1);
	}
	
 
}
