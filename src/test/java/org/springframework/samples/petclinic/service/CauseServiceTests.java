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



import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
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
class CauseServiceTests {        
        
        @Autowired
	protected CauseService causeService;
        

	@Test
	void shouldFindCauseWithCorrectId() {
		final Cause c1 = this.causeService.findCauseById(1);
		org.assertj.core.api.Assertions.assertThat(c1.getOrganization()).isEqualTo("GreenPeace");
		org.assertj.core.api.Assertions.assertThat(c1.getName()).isEqualTo("Limpieza de vertido de petroleo");

	}

	@Test
	@Transactional
	void shouldInsertCauseIntoDatabaseAndGenerateId() {
		
		final Cause cause = new Cause();
		cause.setName("prueba");
		cause.setDescription("probando probando");
		cause.setOrganization("XLStudios");
		cause.setTarget(500);
		cause.setClosed(false);
		cause.setDonated(100);
		this.causeService.save(cause);
		org.assertj.core.api.Assertions.assertThat(cause.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(cause.getName()).isEqualTo("prueba");
		
	}
	
	
	@Test
	void shouldFindAllCauses() throws Exception {
		final List<Cause> causes = this.causeService.findAll();
		final int tam = causes.size();
		org.assertj.core.api.Assertions.assertThat(tam).isEqualTo(3);
	}
	
	@Test
    @Transactional
    void shouldDeleteCause() {
        this.causeService.delete(1);

        final Cause cDel = this.causeService.findCauseById(1);
        org.assertj.core.api.Assertions.assertThat(cDel).isNull();
       
    }
	
 
}
