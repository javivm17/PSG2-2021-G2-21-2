package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.PetService;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTests {

	@Mock
	private PetService clinicService;

	private PetTypeFormatter petTypeFormatter;

	@BeforeEach
	void setup() {
		this.petTypeFormatter = new PetTypeFormatter(this.clinicService);
	}

	@Test
	void testPrint() {
		final PetType petType = new PetType();
		petType.setName("Hamster");
		final String petTypeName = this.petTypeFormatter.print(petType, Locale.ENGLISH);
		Assertions.assertEquals("Hamster", petTypeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(this.clinicService.findPetTypes()).thenReturn(this.makePetTypes());
		final PetType petType = this.petTypeFormatter.parse("Bird", Locale.ENGLISH);
		Assertions.assertEquals("Bird", petType.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(this.clinicService.findPetTypes()).thenReturn(this.makePetTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			this.petTypeFormatter.parse("Fish", Locale.ENGLISH);
		});
	}

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 * @return {@link Collection} of {@link PetType}
	 */
	private Collection<PetType> makePetTypes() {
		final Collection<PetType> petTypes = new ArrayList<>();
		petTypes.add(new PetType() {
			{
				this.setName("Dog");
			}
		});
		petTypes.add(new PetType() {
			{
				this.setName("Bird");
			}
		});
		return petTypes;
	}

}
