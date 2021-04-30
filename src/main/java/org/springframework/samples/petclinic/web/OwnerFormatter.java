package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Component;

@Component
public class OwnerFormatter implements Formatter<Owner>{
	private final DonationService donationService;
	   
    @Autowired
    public OwnerFormatter(DonationService donationService) {
        this.donationService = donationService;
    }
    
	@Override
	public String print(Owner object, Locale locale) {
		return object.getFirstName() + " " + object.getLastName();
	}
	@Override
	public Owner parse(String text, Locale locale) throws ParseException {
		List<Owner> findOwners = this.donationService.findOwners();
        for (Owner ow : findOwners) {
            if ((ow.getFirstName() + " " + ow.getLastName()).equals(text)) {
                return ow;
            }
        }
        throw new ParseException("owner not found: " + text, 0);
	}
}
