package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adoption")

public class AdoptionController {
	
	private PetService petService;
	
	private OwnerService ownerService;
	
	private AdoptionRequestService adoptionRequestService;

	
	@Autowired
	public AdoptionController(PetService petService,OwnerService ownerService, AdoptionRequestService adoptionRequestService) {
		this.petService = petService;
		this.ownerService= ownerService;
		this.adoptionRequestService= adoptionRequestService;
	}

	@GetMapping(value = "/list")
    public String showAdoptablePetsList(Map<String, Object> model) {
		model.put("pets", petService.findAdoptablePets());
		model.put("owners", petService.findOwners());
		return "adoption/listAdoptablePets";
	}
	
	@GetMapping(value="/requests")
	public ModelAndView getPendingRequests(Principal principal) {
		Owner owner = ownerService.getOwnerByUserName(principal.getName());
		List<AdoptionApplications>pendingRequets = adoptionRequestService.getRequests(owner); 
		ModelAndView mov = new ModelAndView("adoption/pendingRequests");
		mov.addObject("requests", pendingRequets.size());
		return mov;

		
	}
}
