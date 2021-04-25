package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	public List<Booking> listBookingsByPetId(int petId) throws DataAccessException{
		return bookingRepository.findBookingsByPetId(petId);
	}

	public boolean isOverlapped(Booking booking) {

		List<Booking> hotels = listBookingsByPetId(booking.getPet().getId());
    	if (!(booking.getInitialDate() == null || booking.getEndDate() == null)) {
    		for(int i=0;i<hotels.size();i++) {
    			// A corresponde a la fecha de inicio de la reserva realizada previamente, B corresponde a la fecha de fin de la reserva realizada previamente
    			// C corresponde a la fecha de inicio de la nueva reserva, D corresponde a la fecha de fin de la nueva reserva
        		boolean AantesdeC = booking.getPet().getBookingsList().get(i).getInitialDate().isBefore(booking.getInitialDate());
        		boolean BdespuesdeC = booking.getPet().getBookingsList().get(i).getEndDate().isAfter(booking.getInitialDate());
        		boolean AantesdeD = booking.getPet().getBookingsList().get(i).getInitialDate().isBefore(booking.getEndDate());
        		boolean BdespuesdeD = booking.getPet().getBookingsList().get(i).getEndDate().isAfter(booking.getEndDate());
        		boolean AigualC = booking.getPet().getBookingsList().get(i).getInitialDate().equals(booking.getInitialDate());
        		boolean BigualD = booking.getPet().getBookingsList().get(i).getEndDate().equals(booking.getEndDate());
        		boolean AdespuesdeC = booking.getPet().getBookingsList().get(i).getInitialDate().isAfter(booking.getInitialDate());
        		boolean BantesdeD = booking.getPet().getBookingsList().get(i).getEndDate().isBefore(booking.getEndDate());
        		
        		if((AantesdeC || AigualC) &&	BdespuesdeC || AantesdeD && (BdespuesdeD || BigualD) || (AigualC && BigualD) || (AdespuesdeC && BantesdeD)) {
        			return true;
        		}
    		}
    	}		return false;
	}
}
