package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	
	private final BookingRepository bookingRepository;

	@Autowired
	public BookingService(final BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}
	
	public Booking saveBooking(final Booking booking) {	
		return this.bookingRepository.save(booking);
	}
	
	public void deleteBooking(final Integer id) {
		this.bookingRepository.deleteById(id);
	}
	
	public List<Booking> getBookings(){
		return this.bookingRepository.findAll();
	}
	
	public List<Booking> listBookingsByPetId(final int petId) throws DataAccessException{
		return this.bookingRepository.findBookingsByPetId(petId);
	}

	public boolean isOverlapped(final Booking booking) {
		final List<Booking> hotels = this.listBookingsByPetId(booking.getPet().getId());
		for(int i=0;i<hotels.size();i++) {

			// A corresponde a la fecha de inicio de la reserva realizada previamente, B corresponde a la fecha de fin de la reserva realizada previamente
			// C corresponde a la fecha de inicio de la nueva reserva, D corresponde a la fecha de fin de la nueva reserva
    		final boolean aAntesDeC = booking.getPet().getBookingsList().get(i).getInitialDate().isBefore(booking.getInitialDate());
    		final boolean bDespuesDeC = booking.getPet().getBookingsList().get(i).getEndDate().isAfter(booking.getInitialDate());
    		final boolean aAntesDeD = booking.getPet().getBookingsList().get(i).getInitialDate().isBefore(booking.getEndDate());
    		final boolean bDespuesDeD = booking.getPet().getBookingsList().get(i).getEndDate().isAfter(booking.getEndDate());
    		final boolean aIgualC = booking.getPet().getBookingsList().get(i).getInitialDate().equals(booking.getInitialDate());
    		final boolean bigualD = booking.getPet().getBookingsList().get(i).getEndDate().equals(booking.getEndDate());
    		final boolean aDespuesDeC = booking.getPet().getBookingsList().get(i).getInitialDate().isAfter(booking.getInitialDate());
    		final boolean bAntesDeD = booking.getPet().getBookingsList().get(i).getEndDate().isBefore(booking.getEndDate());
    		
    		if((aAntesDeC || aIgualC) &&	bDespuesDeC || aAntesDeD && (bDespuesDeD || bigualD) || (aIgualC && bigualD) || (aDespuesDeC && bAntesDeD)) {
    			return true;
    		}
    	}
		return false;

	}
}
