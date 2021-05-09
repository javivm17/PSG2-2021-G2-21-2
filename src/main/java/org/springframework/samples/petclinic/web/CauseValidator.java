package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CauseValidator implements Validator{

	private final String required = "Campo requerido";
	
	@Override
	public boolean supports(final Class<?> clazz) {
		return Cause.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(final Object target, final Errors errors) {
		final Cause cause = (Cause) target;
		final String name = cause.getName();
		final String desc = cause.getDescription();
		final String organization = cause.getOrganization();
		final Integer donTarget = cause.getTarget();
		//Name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", this.required,this.required);
		}
		//Description validation
		if (desc == null || desc.trim().equals("")) {
			errors.rejectValue("description", this.required, this.required);
		}
		//Organization validation
		if (organization == null || organization.trim().equals("")) {
			errors.rejectValue("organization",this.required, this.required);
		}
		//Target validation
		if (donTarget == null) {
			errors.rejectValue("target", this.required, this.required);
		}
		else if(donTarget<=0) {
			errors.rejectValue("target", "La cantidad debe ser positiva", "La cantidad debe ser positiva");
		}
		
	}
}
