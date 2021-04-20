package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes")
public class CauseController {

	private final CauseService causeService;
	
	@Autowired
	public CauseController(final CauseService causeService) {
		this.causeService = causeService;
	}
	
	@GetMapping()
	public String showCauseList(final ModelMap model) {
		final Iterable<Cause> causes = this.causeService.findAll();
		model.put("causes", causes);
		return "causes/causeList";
	}
	
	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(path="/new")
	public String initCreateCause(ModelMap modelMap) {
		modelMap.addAttribute("cause", new Cause());
		return "causes/causeNew";
	}
	
	@PostMapping(value = "/save")
	public String processCreationForm(@Valid Cause cause, BindingResult result, ModelMap model) {	
		if (result.hasErrors()) {
			model.addAttribute("cause", cause);
			return "causes/causeNew";
		}
		else {
			this.causeService.save(cause);
			return showCauseList(model);
		}
	}
	
	
}
