package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	
	private BookingRepository bookingRepository;

	@Autowired
	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}
	
	public void saveBooking(Booking booking) {
		bookingRepository.save(booking);	
	}
	
	
	
}
