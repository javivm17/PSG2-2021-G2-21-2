package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CauseValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Cause.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Cause cause = (Cause) target;
		String name = cause.getName();
		String desc = cause.getDescription();
		String organization = cause.getOrganization();
		Integer donTarget = cause.getTarget();
		//Name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", "Campo requerido", "Campo requerido");
		}
		//Description validation
		if (desc == null || desc.trim().equals("")) {
			errors.rejectValue("description", "Campo requerido", "Campo requerido");
		}
		//Organization validation
		if (organization == null || organization.trim().equals("")) {
			errors.rejectValue("organization", "Campo requerido", "Campo requerido");
		}
		//Target validation
		if (donTarget == null) {
			errors.rejectValue("target", "Campo requerido", "Campo requerido");
		}
		else if(donTarget<=0) {
			errors.rejectValue("target", "La cantidad debe ser positiva", "La cantidad debe ser positiva");
		}
		
	}
}
