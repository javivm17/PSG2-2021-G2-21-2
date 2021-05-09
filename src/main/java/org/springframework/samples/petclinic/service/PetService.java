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

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.AdoptionRequestsRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private final PetRepository petRepository;
	
	private final VisitRepository visitRepository;
	
	private final AdoptionRequestsRepository adoptionRequestsRepository;

	

	@Autowired
	public PetService(final PetRepository petRepository,
			final VisitRepository visitRepository, final AdoptionRequestsRepository adoptionRequestsRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
		this.adoptionRequestsRepository = adoptionRequestsRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return this.petRepository.findPetTypes();
	}
	
	@Transactional
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.save(visit);
	}
	
	@Transactional
    public void deleteVisit(final int idVisit) throws DataAccessException {
        this.visitRepository.deleteById(idVisit);
    }

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) throws DataAccessException {
		return this.petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(final Pet pet) throws DataAccessException, DuplicatedPetNameException {
			final Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && !otherPet.getId().equals(pet.getId()))) {            	
            	throw new DuplicatedPetNameException();
            }else
                this.petRepository.save(pet);                
	}


	public Collection<Visit> findVisitsByPetId(final int petId) {
		return this.visitRepository.findByPetId(petId);
	}
	
	@Transactional
	public void deletePetById(final int id) throws DataAccessException{
		//Se deben borrar las requests asociadas 
		final List<Adoption>ls =this.adoptionRequestsRepository.findRequestsByPet(id);
		for(final Adoption a: ls) {
			this.adoptionRequestsRepository.deleteById(a.getId());
		}
		this.petRepository.deleteById(id); 
	}
	
	@Transactional(readOnly=true)
	public List<Pet> findAdoptablePets(final Integer id){
		return this.petRepository.findAdoptablePets(id);
	}
	
	@Transactional(readOnly=true)
	public List<Owner> findOwners(){
		return this.petRepository.allOwners();
	}
}
