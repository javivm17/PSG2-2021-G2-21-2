package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link VisitController}
 *
 * @author Colin But
 */
@WebMvcTest(controllers = VisitController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
class VisitControllerTests {

	private static final int TEST_PET_ID = 1;


	@MockBean
	private PetService clinicService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		BDDMockito.given(this.clinicService.findPetById(VisitControllerTests.TEST_PET_ID)).willReturn(new Pet());
	}

        @WithMockUser(value = "spring")
        @Test
	void testInitNewVisitForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/*/pets/{petId}/visits/new", VisitControllerTests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateVisitForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessNewVisitFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/*/pets/{petId}/visits/new", VisitControllerTests.TEST_PET_ID).param("name", "George")
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("description", "Visit Description"))                                
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessNewVisitFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/*/pets/{petId}/visits/new", VisitControllerTests.TEST_PET_ID)
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("name", "George"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("visit")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateVisitForm"));
	}

	@WithMockUser(value = "spring")
        @Test 
	void testShowVisits() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/*/pets/{petId}/visits", VisitControllerTests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("visits")).andExpect(MockMvcResultMatchers.view().name("visitList"));
	}

}
