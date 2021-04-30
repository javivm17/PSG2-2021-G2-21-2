package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;


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
	
	@Test
	void shouldBeOverlapped() throws DataAccessException, DuplicatedPetNameException {
		Pet p = petService.findPetById(1);
		Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2021, 1, 23));
		b.setEndDate(LocalDate.of(2021, 9, 21));
		b.setPet(p);
		Booking book =bookingService.saveBooking(b);
		Set<Booking>bookingList= p.getBookings();
		bookingList.add(book);
		p.setBookings(bookingList);
		petService.savePet(p);

		
		
//		(AantesdeC || AigualC) &&	BdespuesdeC 
		Booking b2 = new Booking();
		b2.setInitialDate(LocalDate.of(2021, 1, 23));
		b2.setEndDate(LocalDate.of(2021, 9, 22));
		b2.setPet(p);
//		AantesdeD && (BdespuesdeD || BigualD)
		Booking b3 = new Booking();
		b3.setInitialDate(LocalDate.of(2021, 1, 23));
		b3.setEndDate(LocalDate.of(2021, 1, 24));
		b3.setPet(p);
//		AigualC && BigualD 
		Booking b4 = new Booking();
		b4.setInitialDate(LocalDate.of(2021, 1, 23));
		b4.setEndDate(LocalDate.of(2021, 9, 21));
		b4.setPet(p);
//		AdespuesdeC && BantesdeD
		Booking b5 = new Booking();
		b5.setInitialDate(LocalDate.of(2021, 1, 22));
		b5.setEndDate(LocalDate.of(2021, 9, 20));
		b5.setPet(p);
		
		boolean caso1 = bookingService.isOverlapped(b2);
		boolean caso2 = bookingService.isOverlapped(b3);
		boolean caso3 = bookingService.isOverlapped(b4);
		boolean caso4 = bookingService.isOverlapped(b5);
		
		assertTrue(caso1);
		assertTrue(caso2);
		assertTrue(caso3);
		assertTrue(caso4);
	}
	
	@Test
	void shouldNotBeOverlapped() throws DataAccessException, DuplicatedPetNameException {
		Pet p = petService.findPetById(1);
		Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2022, 1, 23));
		b.setEndDate(LocalDate.of(2022, 9, 21));
		b.setPet(p);
		Booking book =bookingService.saveBooking(b);
		Set<Booking>bookingList= p.getBookings();
		bookingList.add(book);
		p.setBookings(bookingList);
		petService.savePet(p);

		Booking b2 = new Booking();
		b2.setInitialDate(LocalDate.of(2022, 10, 23));
		b2.setEndDate(LocalDate.of(2022, 11, 22));
		b2.setPet(p);
		
		boolean caso = bookingService.isOverlapped(b2);
		assertFalse(caso);

		
	}
	
}
