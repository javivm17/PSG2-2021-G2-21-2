package org.springframework.samples.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/team")
public class itopController {
		
	@GetMapping
    public ModelAndView showAdoptablePetsList() {

		final ModelAndView mav = new ModelAndView("itop/team");
		return mav;
		
	}
}
