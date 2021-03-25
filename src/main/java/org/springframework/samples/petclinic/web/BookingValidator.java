package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Booking;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Booking.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Booking booking = (Booking) target;

		if (booking.getInitialDate()==null) {
			errors.rejectValue("initialDate", "Requerido", "Requerido");	
		}
		
		if (booking.getEndDate()==null) {
			errors.rejectValue("endDate", "Requerido", "Requerido");	
		}
		
		if (booking.getEndDate()!=null && booking.getInitialDate()!=null && booking.getInitialDate().isAfter(booking.getEndDate())) {
			errors.rejectValue("endDate", "La fecha de fin debe ser posterior a la fecha de fecha de inicio", "La fecha de fin debe ser posterior a la fecha de fecha de inicio");
		}
	}

	
}
