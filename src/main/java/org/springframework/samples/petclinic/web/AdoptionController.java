package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adoption")
public class AdoptionController {
	private static final String VIEWS_REQUEST_CREATE_FORM = "adoption/formAdoption";
	
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
		final Adoption request = new Adoption();
		model.put("pet", petService.findPetById(id));
		model.put("owner", owner);
		model.put("adoption", request);
		return AdoptionController.VIEWS_REQUEST_CREATE_FORM;
	}
	
	@PostMapping(value = "/new/{id}")
	public String processCreationForm(@PathVariable("id") Integer id, @Valid Adoption AdoptionApplications, BindingResult result, final ModelMap model, Principal principal) {
		if (result.hasErrors()) {
			Owner owner = ownerService.getOwnerByUserName(principal.getName());
			model.put("pet", petService.findPetById(id));
			model.put("owner", owner);
			model.put("adoption", AdoptionApplications);
			return AdoptionController.VIEWS_REQUEST_CREATE_FORM;
		}
		else {
             this.adoptionService.saveRequest(AdoptionApplications);                    
             return "redirect:/adoption/requestsent";              
			
		}
	}
	
	@GetMapping(value="/requestsent")
	public void successSentForm(Adoption request) {		
	}
		
	
	@GetMapping(value="/requests")
	public ModelAndView getPendingRequests(Principal principal) {
		Owner owner = ownerService.getOwnerByUserName(principal.getName());
		List<Adoption>pendingRequets = adoptionRequestService.getRequests(owner); 
		ModelAndView mov = new ModelAndView("adoption/pendingRequests");
		mov.addObject("requests", pendingRequets);
		mov.addObject("numRequests",pendingRequets.size());
		return mov;	
	}
	
	@GetMapping(value="/accept/{reqId}")
	public String acceptRequest(@PathVariable("reqId") Integer reqId, Principal principal) throws DataAccessException, DuplicatedPetNameException {
		Owner ownerLogeado = ownerService.getOwnerByUserName(principal.getName());
		Adoption request = adoptionRequestService.getRequestById(reqId).orElse(null);
		
		if(request.getPet().getOwner().getId().equals(ownerLogeado.getId()))
			this.adoptionRequestService.acceptRequest(request);
		return "redirect:/adoption/requests";
	}
	
	@GetMapping(value="/deny/{reqId}")
	public String denyString(@PathVariable("reqId") Integer reqId, Principal principal) {
		Owner ownerLogeado = ownerService.getOwnerByUserName(principal.getName());
		Adoption request = adoptionRequestService.getRequestById(reqId).orElse(null);
		
		if(request.getPet().getOwner().getId().equals(ownerLogeado.getId()))
			adoptionRequestService.removeRequest(request.getId());
			
		return "redirect:/adoption/requests";
	}
	
}