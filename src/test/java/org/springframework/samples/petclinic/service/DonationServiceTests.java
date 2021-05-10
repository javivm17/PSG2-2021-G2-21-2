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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
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
class DonationServiceTests {        
        @Autowired
	protected DonationService donationService;
        
        @Autowired
	protected CauseService causeService;
        
        @Autowired
	protected OwnerService ownerService;

	@Test
	void shouldFindDonationWithCorrectId() {
		final Donation don1 = this.donationService.findDonationById(1);
		org.assertj.core.api.Assertions.assertThat(don1.getAmount()).isEqualTo(50);
		org.assertj.core.api.Assertions.assertThat(don1.getCause().getName()).isEqualTo("Limpieza de vertido de petroleo");

	}

	@Test
	@Transactional
	void shouldInsertDonationIntoDatabaseAndGenerateId() {
		final Owner owner1 = this.ownerService.findOwnerById(1);
		final Cause c1 = this.causeService.findCauseById(1);
		
		final Donation don = new Donation();
		don.setAmount(11);
		don.setCause(c1);
		don.setOwner(owner1);
		this.donationService.saveDonation(don);
		org.assertj.core.api.Assertions.assertThat(don.getId()).isNotNull();
		
	}
	
	
	@Test
	void shouldFindDonationsByCauseId() throws Exception {
		final Collection<Donation> donations = this.donationService.findDonationsByCauseId(3);
		org.assertj.core.api.Assertions.assertThat(donations).hasSize(3);
		final Donation[] visitArr = donations.toArray(new Donation[donations.size()]);
		org.assertj.core.api.Assertions.assertThat(visitArr[0].getAmount()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(visitArr[0].getDate()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(visitArr[0].getId()).isEqualTo(3);
	}
	
	@Test
    @Transactional
    void shouldDeleteDonation() {
		final Donation don = this.donationService.findDonationById(1);
        this.donationService.deleteDonation(don);

        final Donation donDel = this.donationService.findDonationById(1);
        org.assertj.core.api.Assertions.assertThat(donDel).isNull();
       
    }
	
	@Test
	@Transactional
	void shouldFindOwners() {
		final List<Owner> owns= this.donationService.findOwners();
        org.assertj.core.api.Assertions.assertThat(owns).isNotNull();
	}
	
 
}
