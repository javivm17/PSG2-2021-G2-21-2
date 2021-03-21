package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BookingController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
public class BookingControllerTest {
	
	private static final int TEST_PET_ID = 1;
	
	@Autowired
	private BookingController bookingController;
	
	@MockBean
	BookingService bookingService; 
	
	@MockBean
	PetService petService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitNewBookingForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/booking/new", TEST_PET_ID)).andExpect(status().isOk())
			.andExpect(view().name("pets/createBookingForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/*/pets/{petId}/booking/new", TEST_PET_ID).param("initialDate", "2021/03/17")
						.with(csrf())
						.param("endDate", "2024/03/19"))                                
            .andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	 
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormHasErrorsByEndDate() throws Exception {
		mockMvc.perform(post("/owners/*/pets/{petId}/booking/new", TEST_PET_ID)
						.with(csrf())
						.param("initialDate", "2021/03/17"))
			.andExpect(model().attributeHasErrors("booking")).andExpect(status().isOk())
			.andExpect(view().name("pets/createBookingForm"));
}
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormHasErrorsByInitialDate() throws Exception {
		mockMvc.perform(post("/owners/*/pets/{petId}/booking/new", TEST_PET_ID)
						.with(csrf())
						.param("endDate", "2021/03/17"))
			.andExpect(model().attributeHasErrors("booking")).andExpect(status().isOk())
			.andExpect(view().name("pets/createBookingForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewVisitFormHasErrorsByDates() throws Exception {
		mockMvc.perform(post("/owners/*/pets/{petId}/booking/new", TEST_PET_ID).param("initialDate", "2021/03/19")
						.with(csrf())
						.param("endDate", "2021/03/17"))
			.andExpect(model().attributeHasErrors("booking")).andExpect(status().isOk())
			.andExpect(view().name("pets/createBookingForm"));
}
	
}
