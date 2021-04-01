package org.springframework.samples.petclinic.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BookingServiceTests {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PetService petService;
	
	@Test
	void shouldSaveBooking() {
		Pet p = petService.findPetById(1);
		Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2021, 1, 23));
		b.setEndDate(LocalDate.of(2021, 9, 21));
		b.setPet(p);	
		
		Integer cont1= bookingService.getBookings().size(); 
		bookingService.saveBooking(b);
		Integer cont2= bookingService.getBookings().size(); 
		
		assertThat(cont1<cont2);
	}
	
	@Test
	void shouldDeleteBooking() {
		Pet p = petService.findPetById(1);
		Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2021, 1, 23));
		b.setEndDate(LocalDate.of(2021, 9, 21));
		b.setPet(p);
		
		bookingService.saveBooking(b);
		Integer cont1= bookingService.getBookings().size(); 
		
		Booking b2= bookingService.getBookings().get(0);
		bookingService.deleteBooking(b2.getId());
		Integer cont2= bookingService.getBookings().size(); 
	
		assertThat(cont2<cont1);
	}
	
	
	
}
