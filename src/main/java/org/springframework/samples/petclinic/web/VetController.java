/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/vets/{vetId}")
public class VetController {

	private static final String FORM = "vets/createOrUpdateVetForm";
	private final VetService vetService;

	@Autowired
	public VetController(VetService clinicService) {
		this.vetService = clinicService;
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	
	@GetMapping(value = "/vets/new")
	public String initCreationForm(Vet vet, ModelMap model) {
		model.put("vet", new Vet());
		return FORM;
	}

	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("vet", vet);
			return FORM;
		}
		else {/**
                    try{
                    	owner.addVet(vet);
                    	this.vetService.saveVet(vet);
                    }catch(DuplicatedVetNameException ex){
                        result.rejectValue("name", "duplicate", "already exists");
                        return FORM;
                    }**/
                    return "redirect:/vets/{vetId}";
		}
	}

	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateForm(@PathVariable("vetId") int vetId, ModelMap model) {
		Vet vet = this.vetService.findVetById(vetId);
		model.put("vet", vet);
		return FORM;
	}

    /**
     *
     * @param vet
     * @param result
     * @param vetId
     * @param model
     * @param owner
     * @param model
     * @return
     */
        @PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateForm(@Valid Vet vet, BindingResult result,@PathVariable("vetId") int vetId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return FORM;
		}
		else {/**
                        Vet vetToUpdate=this.vetService.findVetById(vetId);
                        BeanUtils.copyProperties(vet, vetToUpdate, "id","owner","visits");                                                                                  
                    try {                    
                        this.vetService.saveVet(vetToUpdate);                    
                    } catch (DuplicatedVetNameException ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return FORM;
                    }**/
			return "redirect:/vets/{vetId}";
		}
	}

}
