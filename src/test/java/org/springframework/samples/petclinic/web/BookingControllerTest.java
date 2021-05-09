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
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = BookingController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
class BookingControllerTest {
	
	private static final int TEST_PET_ID = 1;
	
	
	@MockBean
	BookingService bookingService; 
	
	@MockBean
	PetService petService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		BDDMockito.given(this.petService.findPetById(BookingControllerTest.TEST_PET_ID)).willReturn(new Pet());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitNewBookingForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/*/pets/{petId}/booking/new", BookingControllerTest.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("pets/createBookingForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/*/pets/{petId}/booking/new", BookingControllerTest.TEST_PET_ID).param("initialDate", "2021/03/17")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("endDate", "2024/03/19"))                                
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"));
	}
	 
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormHasErrorsByEndDate() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/*/pets/{petId}/booking/new", BookingControllerTest.TEST_PET_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("initialDate", "2021/03/17"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("booking")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("pets/createBookingForm"));
}
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormHasErrorsByInitialDate() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/*/pets/{petId}/booking/new", BookingControllerTest.TEST_PET_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("endDate", "2021/03/17"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("booking")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("pets/createBookingForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormHasErrorsByDates() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/*/pets/{petId}/booking/new", BookingControllerTest.TEST_PET_ID).param("initialDate", "2021/03/19")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("endDate", "2021/03/17"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("booking")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("pets/createBookingForm"));
}
	
}
