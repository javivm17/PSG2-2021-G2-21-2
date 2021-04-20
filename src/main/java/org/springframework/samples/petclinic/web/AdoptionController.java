package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adoption")

public class AdoptionController {
	private static final String VIEWS_REQUEST_CREATE_OR_UPDATE_FORM = "adoption/formAdoption";
	
	private PetService petService;
	
	private OwnerService ownerService;
	
	private AdoptionRequestService adoptionRequestService;
	
	private AdoptionService adoptionService;


	
	@Autowired
	public AdoptionController(PetService petService,OwnerService ownerService, AdoptionRequestService adoptionRequestService, AdoptionService adoptionService) {
		this.petService = petService;
		this.ownerService= ownerService;
		this.adoptionRequestService= adoptionRequestService;
		this.adoptionService = adoptionService;
	}

	@GetMapping(value = "/list")
    public ModelAndView showAdoptablePetsList(Principal principal) {
		List<Pet> pets = petService.findAdoptablePets(ownerService.getOwnerByUserName(principal.getName()).getId());
		List<Owner> owners = petService.findOwners();
		final ModelAndView mav = new ModelAndView("adoption/listAdoptablePets");
		mav.addObject("pets", pets);
		mav.addObject("owners", owners);
		return mav;
	}
	
	@RequestMapping(value = "/{ownerId}/{petId}/{option}", method={RequestMethod.PUT, RequestMethod.GET})
	public String processUpdateAdoption(@PathVariable("petId") int petId,@PathVariable("ownerId") int ownerId,@PathVariable("option") int option, final ModelMap model) {
		Pet pet = this.petService.findPetById(petId);
		Boolean opcion = null;
		if (option == 1) opcion = true;
		if (option ==0) opcion = false;
		pet.setInAdoption(opcion);                                                                         
                try {                    
                	this.petService.savePet(pet);                    
                } catch (final DuplicatedPetNameException ex) {
                }
		return "redirect:/owners/{ownerId}";
	}
	
	@GetMapping(value = "/new/{id}")
	public String initCreationForm(@PathVariable("id") Integer id, final Map<String, Object> model, Principal principal) {
		Owner owner = ownerService.getOwnerByUserName(principal.getName());
		final AdoptionApplications request = new AdoptionApplications();
		System.out.println(id);
		model.put("pet", petService.findPetById(id));
		model.put("owner", owner);
		model.put("request", request);
		return AdoptionController.VIEWS_REQUEST_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(AdoptionApplications request) {
			System.out.println("prueba");
			this.adoptionService.saveRequest(request);			
			return "redirect:/";
		
	}
	
	@GetMapping(value="/requests")
	public ModelAndView getPendingRequests(Principal principal) {
		Owner owner = ownerService.getOwnerByUserName(principal.getName());
		List<AdoptionApplications>pendingRequets = adoptionRequestService.getRequests(owner); 
		ModelAndView mov = new ModelAndView("adoption/pendingRequests");
		mov.addObject("requests", pendingRequets);
		mov.addObject("numRequests",pendingRequets.size());
		return mov;	
	}
	
	@GetMapping(value="/accept/{reqId}")
	public String acceptRequest(@PathVariable("reqId") Integer reqId, Principal principal) throws DataAccessException, DuplicatedPetNameException {
		Owner ownerLogeado = ownerService.getOwnerByUserName(principal.getName());
		AdoptionApplications request = adoptionRequestService.getRequestById(reqId).orElse(null);
		
		if(request.getPet().getOwner().getId().equals(ownerLogeado.getId()))
			this.adoptionRequestService.acceptRequest(request);
		return "redirect:/adoption/requests";
	}
	
	@GetMapping(value="/deny/{reqId}")
	public String denyString(@PathVariable("reqId") Integer reqId, Principal principal) {
		Owner ownerLogeado = ownerService.getOwnerByUserName(principal.getName());
		AdoptionApplications request = adoptionRequestService.getRequestById(reqId).orElse(null);
		
		if(request.getPet().getOwner().getId().equals(ownerLogeado.getId()))
			adoptionRequestService.removeRequest(request.getId());
			
		return "redirect:/adoption/requests";
	}
	
}