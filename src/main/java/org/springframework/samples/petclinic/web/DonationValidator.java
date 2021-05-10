package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DonationValidator implements Validator{

	@Override
	public boolean supports(final Class<?> clazz) {
		return Donation.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(final Object target, final Errors errors) {
		final Donation donation = (Donation) target;
		final Integer amount = donation.getAmount();
		//Amount validation
		if (amount == null) {
			errors.rejectValue("amount", "Campo requerido", "Campo requerido");
		}
		else if(amount<=0) {
			errors.rejectValue("amount", "La cantidad debe ser positiva", "La cantidad debe ser positiva");
		}
		
	}
}
