package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Booking;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookingValidator implements Validator {

	private final String requerido = "Requerido"; 
	
	@Override
	public boolean supports(final Class<?> clazz) {
		return Booking.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(final Object target, final Errors errors) {
		final Booking booking = (Booking) target;
		   		
        if (booking.getInitialDate()==null) {
        	errors.rejectValue("initialDate", this.requerido, this.requerido);	
        }
        			
        if (booking.getEndDate()==null) {
        	errors.rejectValue("endDate", this.requerido, this.requerido);	
        }
        			
        if (booking.getEndDate()!=null && booking.getInitialDate()!=null && booking.getInitialDate().isAfter(booking.getEndDate())) {
        	errors.rejectValue("endDate", "La fecha de fin debe ser posterior a la fecha de fecha de inicio", "La fecha de fin debe ser posterior a la fecha de fecha de inicio");
        }
        
        if(booking.getInitialDate().isBefore(LocalDate.now())) {
        	errors.rejectValue("initialDate", "La fecha de inicio debe ser posterior a la fecha de hoy", "La fecha de inicio debe ser posterior a la fecha de hoy");
        }
        
        if(booking.getEndDate().isBefore(LocalDate.now())) {
        	errors.rejectValue("endDate", "La fecha de fin debe ser posterior a la fecha de hoy", "La fecha de fin debe ser posterior a la fecha de hoy");
        }
        
	}
}
