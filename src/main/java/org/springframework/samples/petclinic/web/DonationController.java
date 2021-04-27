package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {

	@Autowired
	private DonationService donationService;
	
	@Autowired
	private CauseService causeService;
	
	@Autowired
	private DonationValidator validator;

	@InitBinder("donation")
	public void initDonationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id, cause_id, owner_id");
	}
	
	@ModelAttribute("cause")
	public Cause findCause(@PathVariable("causeId") final int causeId) {
		return this.causeService.findCauseById(causeId);
	}
	
	@ModelAttribute("donation")
	public Donation loadCauseWithDonation(@PathVariable("causeId") final int causeId) {
		final Cause cause = this.causeService.findCauseById(causeId);
		final Donation donation = new Donation();
		donation.setCause(cause);
		return donation;
	}
	
	@GetMapping(value = "/donations/new")
	public String initNewDonationForm(@PathVariable("causeId") final int causeId,  final Map<String, Object> model) {		
		return "causes/createDonationForm";
	}
	
	@PostMapping(value = "/donations/new")
	public String processNewDonationForm(@Valid final Donation donation, final BindingResult result) {
		if (result.hasErrors()) {
			return "causes/createDonationForm";
		}
		else {
			donation.setDate(LocalDate.now());
			donation.setOwner(this.donationService.getLoggedOwner());
			this.donationService.saveDonation(donation);
			
			Cause cs = donation.getCause();
			cs.setDonated(cs.getDonated() + donation.getAmount());
			this.causeService.save(cs);
			return "redirect:/causes/{causeId}";
		}
	}
	
	@GetMapping(value = "")
	public String showDetails(@PathVariable final int causeId, final Map<String, Object> model) {
		model.put("donations", this.donationService.findDonationsByCauseId(causeId));
		model.put("cause", this.causeService.findCauseById(causeId));
		return "causes/causeDetails";
	}
	
}
