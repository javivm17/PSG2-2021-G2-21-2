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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private final VetRepository vetRep;


	@Autowired
	public VetService(final VetRepository vetRepository) {
		this.vetRep = vetRepository;
	}
	
	@Transactional(readOnly=true)
	public List<Vet> findVets() {
		final List<Vet> res = new ArrayList<>();
		this.vetRep.findAll().forEach(x -> res.add(x));
		return res;
	}
	
	@Transactional(readOnly=true)
	public  Optional<Vet> findVetById(final int id){ 
		return this.vetRep.findById(id);
	}

	@Transactional
	public  void save(final Vet vet) {   
		this.vetRep.save(vet);
	}

	@Transactional
	public  void delete(final Vet vet) { 
		this.vetRep.delete(vet);
	}
	
	@Transactional(readOnly=true)
	public List<Specialty> findSpecialties() {
		return this.vetRep.findSpecialties();
	}
	
	@Transactional(readOnly=true)
	public List<Specialty> findMissingSpecialties(final Vet vet) {
		if (vet == null)
			return this.vetRep.findSpecialties();
		final List<Specialty> all = this.vetRep.findSpecialties();
		all.removeAll(vet.getSpecialties());
		return all;
	}

}
