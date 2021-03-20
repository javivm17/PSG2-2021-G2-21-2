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
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/vets")
public class VetController {

	private static final String FORM = "vets/createOrUpdateVetForm";
	
	private final VetService vetService;

	@Autowired
	public VetController(VetService clinicService) {
		this.vetService = clinicService;
	}

	@GetMapping()
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	
	@GetMapping(value = {"/xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	
	
	@GetMapping(value = "/new")
	public String initCreationForm(Vet vet, ModelMap model) {
		model.put("vet", new Vet());
		return FORM;
	}

	@PostMapping(value = "/save")
	public String processCreationForm(@Valid Vet vet, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("vet", vet);
			return FORM;
		}
		else {
			vetService.save(vet);
			return "redirect:/vets";
		}
	}

	@GetMapping(value = "/{vetId}")
	public String showVetInfo(@PathVariable("vetId") int vetId, Map<String, Object> model) {
		Vet vet = vetService.findVetById(vetId).orElse(null);
		if (vet == null)
			return "vets/vetList";
		else {
			model.put("vet", vet);
			return "vets/vetDetails";
		}
		
	}
	
	@PostMapping(value = "/{vetId}/add/{specId}")
	public String addSpecialties(@PathVariable("vetId") int vetId,
			@PathVariable("specId") int specId, Map<String, Object> model) {
		Vet vet = vetService.findVetById(vetId).get();
		List<Specialty> specs = vetService.findSpecialties();
		Specialty sp = EntityUtils.getById(specs, Specialty.class, specId);
		vet.deleteSpecialty(sp);
		model.put("vet", vet);
		vetService.save(vet);
		return "vets/vetDetails";
	}
	
	@PostMapping(value = "/{vetId}/delete/{specId}")
	public String deleteSpecialties(@PathVariable("vetId") int vetId,
			@PathVariable("specId") int specId, Map<String, Object> model) {
		Vet vet = vetService.findVetById(vetId).get();
		List<Specialty> specs = vetService.findSpecialties();
		Specialty sp = EntityUtils.getById(specs, Specialty.class, specId);
		vet.addSpecialty(sp);
		model.put("vet", vet);
		vetService.save(vet);
		return "vets/vetDetails";
	}
	
	@GetMapping(value = "/{vetId}/edit")
	public String initUpdateForm(@PathVariable("vetId") int vetId, ModelMap model) {
		Vet vet = this.vetService.findVetById(vetId).get();
		model.put("vet", vet);
		return FORM;
	}

    @PostMapping(value = "/{vetId}/edit")
	public String processUpdateForm(@Valid Vet vet, BindingResult result,@PathVariable("vetId") int vetId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return FORM;
		}
		else {
			vetService.save(vet);
			return "redirect:/vets";
		}
	}

}
