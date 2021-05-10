package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
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
class BookingServiceTests {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PetService petService;
	
	@Test
	void shouldSaveBooking() {
		final Pet p = this.petService.findPetById(1);
		final Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2021, 1, 23));
		b.setEndDate(LocalDate.of(2021, 9, 21));
		b.setPet(p);	
		
		final Integer cont1= this.bookingService.getBookings().size(); 
		this.bookingService.saveBooking(b);
		final Integer cont2= this.bookingService.getBookings().size(); 
		
		Assertions.assertTrue(cont1<cont2);
	}
	
	@Test
	void shouldDeleteBooking() {
		final Pet p = this.petService.findPetById(1);
		final Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2021, 1, 23));
		b.setEndDate(LocalDate.of(2021, 9, 21));
		b.setPet(p);
		
		this.bookingService.saveBooking(b);
		final Integer cont1= this.bookingService.getBookings().size(); 
		
		final Booking b2= this.bookingService.getBookings().get(0);
		this.bookingService.deleteBooking(b2.getId());
		final Integer cont2= this.bookingService.getBookings().size(); 
	
		Assertions.assertTrue(cont2<cont1);
	}
	
	@Test
	void shouldBeOverlapped() throws DataAccessException, DuplicatedPetNameException {
		final Pet p = this.petService.findPetById(1);
		final Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2021, 1, 23));
		b.setEndDate(LocalDate.of(2021, 9, 21));
		b.setPet(p);
		final Booking book =this.bookingService.saveBooking(b);
		final Set<Booking>bookingList= p.getBookings();
		bookingList.add(book);
		p.setBookings(bookingList);
		this.petService.savePet(p);

		
		
//		(AantesdeC || AigualC) &&	BdespuesdeC 
		final Booking b2 = new Booking();
		b2.setInitialDate(LocalDate.of(2021, 1, 23));
		b2.setEndDate(LocalDate.of(2021, 9, 22));
		b2.setPet(p);
//		AantesdeD && (BdespuesdeD || BigualD)
		final Booking b3 = new Booking();
		b3.setInitialDate(LocalDate.of(2021, 1, 23));
		b3.setEndDate(LocalDate.of(2021, 1, 24));
		b3.setPet(p);
//		AigualC && BigualD 
		final Booking b4 = new Booking();
		b4.setInitialDate(LocalDate.of(2021, 1, 23));
		b4.setEndDate(LocalDate.of(2021, 9, 21));
		b4.setPet(p);
//		AdespuesdeC && BantesdeD
		final Booking b5 = new Booking();
		b5.setInitialDate(LocalDate.of(2021, 1, 22));
		b5.setEndDate(LocalDate.of(2021, 9, 20));
		b5.setPet(p);
		
		final boolean caso1 = this.bookingService.isOverlapped(b2);
		final boolean caso2 = this.bookingService.isOverlapped(b3);
		final boolean caso3 = this.bookingService.isOverlapped(b4);
		final boolean caso4 = this.bookingService.isOverlapped(b5);
		
		Assertions.assertTrue(caso1);
		Assertions.assertTrue(caso2);
		Assertions.assertTrue(caso3);
		Assertions.assertTrue(caso4);
	}
	
	@Test
	void shouldNotBeOverlapped() throws DataAccessException, DuplicatedPetNameException {
		final Pet p = this.petService.findPetById(1);
		final Booking b = new Booking();
		b.setInitialDate(LocalDate.of(2022, 1, 23));
		b.setEndDate(LocalDate.of(2022, 9, 21));
		b.setPet(p);
		final Booking book =this.bookingService.saveBooking(b);
		final Set<Booking>bookingList= p.getBookings();
		bookingList.add(book);
		p.setBookings(bookingList);
		this.petService.savePet(p);

		final Booking b2 = new Booking();
		b2.setInitialDate(LocalDate.of(2022, 10, 23));
		b2.setEndDate(LocalDate.of(2022, 11, 22));
		b2.setPet(p);
		
		final boolean caso = this.bookingService.isOverlapped(b2);
		Assertions.assertFalse(caso);

		
	}
	
}
