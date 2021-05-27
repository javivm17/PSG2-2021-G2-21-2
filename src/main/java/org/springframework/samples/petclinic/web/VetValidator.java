package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VetValidator implements Validator{

private final String REQUIRED = "Campo requerido";
private final String REGEX = ".*\\d.*";
	
	@Override
	public boolean supports(final Class<?> clazz) {
		return Vet.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(final Object target, final Errors errors) {
		final Vet vet = (Vet) target;
		String name = vet.getFirstName();
		String surname = vet.getLastName();
		
		//First name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("firstName", this.REQUIRED,this.REQUIRED);
		}
		else if(name.matches(REGEX)) {
			errors.rejectValue("firstName", "El nombre no puede contener números", "El nombre no puede contener números");
		}
		
		//Last name validation
		if (surname == null || surname.trim().equals("")) {
			errors.rejectValue("lastName", this.REQUIRED, this.REQUIRED);
		}
		else if(surname.matches(REGEX)) {
			errors.rejectValue("lastName", "El apellido no puede contener números", "El apellido no puede contener números");
		}
		
	}
}
