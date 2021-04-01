package org.springframework.samples.petclinic.service;

import java.util.List;

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
	
	public void deleteBooking(Integer id) {
		bookingRepository.deleteById(id);
	}
	
	public List<Booking> getBookings(){
		return bookingRepository.findAll();
	}
	
}
