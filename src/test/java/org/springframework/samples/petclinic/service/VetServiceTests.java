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

import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
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
class VetServiceTests {

	@Autowired
	protected VetService vetService;	

	@Test
	void findVetsTest() {
		final Iterator<Vet> vets = this.vetService.findVets().iterator();

		final Vet james = vets.next();
		Assertions.assertThat(james.getFirstName()).isEqualTo("James");
		Assertions.assertThat(james.getLastName()).isEqualTo("Carter");
		Assertions.assertThat(james.getNrOfSpecialties()).isZero();
		
		Assertions.assertThat(vets.next().getFirstName()).isEqualTo("Helen");
		Assertions.assertThat(vets.next().getFirstName()).isEqualTo("Linda");
		Assertions.assertThat(vets.next().getFirstName()).isEqualTo("Rafael");
		Assertions.assertThat(vets.next().getFirstName()).isEqualTo("Henry");
		Assertions.assertThat(vets.next().getFirstName()).isEqualTo("Sharon");
		org.junit.jupiter.api.Assertions.assertFalse(vets.hasNext());
		
	}

	@Test
	void findVetByIdTest() {
		final Vet vet = this.vetService.findVetById(3).get();
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		Assertions.assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("cirugia");
		Assertions.assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("odontologia");
	}
	
	@Test
	@Transactional
	void saveTest() {
		final Vet sharon = this.vetService.findVetById(6).get();
		final List<Specialty> list = this.vetService.findSpecialties();
		sharon.addSpecialty(list.get(0));
		sharon.addSpecialty(list.get(1));
		this.vetService.save(sharon);
		Assertions.assertThat(sharon.getNrOfSpecialties()).isEqualTo(2);
		Assertions.assertThat(sharon.getSpecialties().get(0).getName()=="cirugia");
        Assertions.assertThat(sharon.getSpecialties().get(0).getName()=="odontologia");
	}
	
	@Test
	@Transactional
	void deleteTest() {
		final Vet linda = this.vetService.findVetById(3).get();
		this.vetService.delete(linda);
		org.junit.jupiter.api.Assertions.assertFalse(this.vetService.findVetById(3).isPresent());
	}
	
	@Test
	@Transactional
	void findSpecialtiesTest() {
		final List<Specialty> list = this.vetService.findSpecialties();
		Assertions.assertThat(list).hasSize(3);
		Assertions.assertThat(list.get(0).getName()).hasToString("radiologia");
		Assertions.assertThat(list.get(1).getName()).hasToString("cirugia");
		Assertions.assertThat(list.get(2).getName()).hasToString("odontologia");
	}
	
	@Test
	@Transactional
	void findMissingSpecialtiesTest() {
		final Vet helen = this.vetService.findVetById(2).get();
		Assertions.assertThat(helen.getNrOfSpecialties()).isEqualTo(1);
		Assertions.assertThat(helen.getSpecialties().get(0).getName()).hasToString("radiologia");
		
		final List<Specialty> specs = this.vetService.findMissingSpecialties(helen);
		Assertions.assertThat(specs).hasSize(2);
		Assertions.assertThat(specs.get(0).getName()).hasToString("cirugia");
		Assertions.assertThat(specs.get(1).getName()).hasToString("odontologia");
	}
	
	@Test
	@Transactional
	void findMissingSpecialtiesNullVetTest() {
		final List<Specialty> specs = this.vetService.findMissingSpecialties(null);
		
		Assertions.assertThat(specs).hasSize(3);
		Assertions.assertThat(specs.get(0).getName()).hasToString("radiologia");
		Assertions.assertThat(specs.get(1).getName()).hasToString("cirugia");
		Assertions.assertThat(specs.get(2).getName()).hasToString("odontologia");
	}
	

}
