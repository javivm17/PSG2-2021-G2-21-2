package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {

	private final BookingService bookingService;
	
	private final  PetService petService;

	private static String createBookingFormView = "pets/createBookingForm";
	
	@Autowired
	public BookingController(final BookingService bookingService, final PetService petService) {
		this.bookingService = bookingService;
		this.petService = petService;
	}
	
	@InitBinder("booking")
	public void initBookingBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new BookingValidator());
	}
	
	@ModelAttribute("booking")
	public Booking loadPetWithVisit(@PathVariable("petId") final int petId) {
		final Booking booking = new Booking();
		final Pet pet = this.petService.findPetById(petId);		
		booking.setPet(pet);
		return booking;
	}
	
	@GetMapping(value = "/owners/*/pets/{petId}/booking/new")
	public String showForm(@PathVariable("petId") final int petId, final Map<String, Object> model) {
		return BookingController.createBookingFormView; 
	}
	
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/booking/new")
	public String processNewVisitForm(@Valid final Booking booking, final BindingResult result) {
		if (result.hasErrors()) {
			return BookingController.createBookingFormView;
		}
		else {
			final boolean isOverlapped = this.bookingService.isOverlapped(booking);
			if(isOverlapped) {
    			result.rejectValue("endDate", "Existe una reserva para esta mascota en estas mismas fechas", "Existe una reserva para esta mascota en estas mismas fechas");
    			return BookingController.createBookingFormView;				
			}
			this.bookingService.saveBooking(booking);
			return "redirect:/owners/{ownerId}"; 
		}
	}
	
	@GetMapping("/owners/{ownerId}/pets/{petId}/booking/delete/{bookingId}")
	public String deleteBooking(@PathVariable("bookingId") final int bookingId,@PathVariable("ownerId") final int ownerId) {
		this.bookingService.deleteBooking(bookingId);
		return "redirect:/owners/{ownerId}";
	}

}
