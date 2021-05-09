package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adoption")
public class AdoptionController {
	private static final String VIEWS_REQUEST_CREATE_FORM = "adoption/formAdoption";
	
	private final PetService petService;
	
	private final OwnerService ownerService;
	
	private final AdoptionRequestService adoptionRequestService;
	
	private final AdoptionService adoptionService;

	@Autowired
	public AdoptionController(final PetService petService,final OwnerService ownerService, final AdoptionRequestService adoptionRequestService, final AdoptionService adoptionService) {
		this.petService = petService;
		this.ownerService= ownerService;
		this.adoptionRequestService= adoptionRequestService;
		this.adoptionService = adoptionService;
	}

	@GetMapping(value = "/list")
    public ModelAndView showAdoptablePetsList(final Principal principal) {
		final List<Pet> pets = this.petService.findAdoptablePets(this.ownerService.getOwnerByUserName(principal.getName()).getId());
		final List<Owner> owners = this.petService.findOwners();
		final ModelAndView mav = new ModelAndView("adoption/listAdoptablePets");
		mav.addObject("pets", pets);
		mav.addObject("owners", owners);
		return mav;
	}
	
	@RequestMapping(value = "/{ownerId}/{petId}/{option}", method={RequestMethod.PUT, RequestMethod.GET})
	public String processUpdateAdoption(@PathVariable("petId") final int petId,@PathVariable("ownerId") final int ownerId,@PathVariable("option") final int option, final ModelMap model) throws DataAccessException, DuplicatedPetNameException {
		final Pet pet = this.petService.findPetById(petId);
		Boolean opcion = null;
		if (option == 1) opcion = true;
		if (option ==0) opcion = false;
		pet.setInAdoption(opcion);                                                                                           
        this.petService.savePet(pet);                    
		return "redirect:/owners/{ownerId}";
	}
	
	@GetMapping(value = "/new/{id}")
	public String initCreationForm(@PathVariable("id") final Integer id, final Map<String, Object> model, final Principal principal) {
		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		final Adoption request = new Adoption();
		model.put("pet", this.petService.findPetById(id));
		model.put("owner", owner);
		model.put("adoption", request);
		return AdoptionController.VIEWS_REQUEST_CREATE_FORM;
	}
	
	@PostMapping(value = "/new/{id}")
	public String processCreationForm(@PathVariable("id") final Integer id, @Valid final Adoption adoptionApplication, final BindingResult result, final ModelMap model, final Principal principal) {
		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		if (result.hasErrors()) {
			model.put("pet", this.petService.findPetById(id));
			model.put("owner", owner);
			model.put("adoption", adoptionApplication);
			return AdoptionController.VIEWS_REQUEST_CREATE_FORM;
		}
		else {
			adoptionApplication.setOwner(owner);
            this.adoptionService.saveRequest(adoptionApplication);                    
            return "redirect:/adoption/requestsent";              
			
		}
	}
	
	@GetMapping(value="/requestsent")
	public void successSentForm() {
		//Este controlador es necesario para el formulario de cracion.
	}
		
	
	@GetMapping(value="/requests")
	public ModelAndView getPendingRequests(final Principal principal) {
		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		final List<Adoption>pendingRequets = this.adoptionRequestService.getRequests(owner); 
		final ModelAndView mov = new ModelAndView("adoption/pendingRequests");
		mov.addObject("requests", pendingRequets);
		mov.addObject("numRequests",pendingRequets.size());
		return mov;	
	}
	
	@GetMapping(value="/accept/{reqId}")
	public String acceptRequest(@PathVariable("reqId") final Integer reqId, final Principal principal) throws DataAccessException, DuplicatedPetNameException {
		final Owner ownerLogeado = this.ownerService.getOwnerByUserName(principal.getName());
		final Adoption request = this.adoptionRequestService.getRequestById(reqId).orElse(null);
		
		if(request.getPet().getOwner().getId().equals(ownerLogeado.getId()))
			this.adoptionRequestService.acceptRequest(request);
		return "redirect:/adoption/requests";
	}
	
	@GetMapping(value="/deny/{reqId}")
	public String denyString(@PathVariable("reqId") final Integer reqId, final Principal principal) {
		final Owner ownerLogeado = this.ownerService.getOwnerByUserName(principal.getName());
		final Adoption request = this.adoptionRequestService.getRequestById(reqId).orElse(null);
		
		if(request.getPet().getOwner().getId().equals(ownerLogeado.getId()))
			this.adoptionRequestService.removeRequest(request.getId());
			
		return "redirect:/adoption/requests";
	}
	
}