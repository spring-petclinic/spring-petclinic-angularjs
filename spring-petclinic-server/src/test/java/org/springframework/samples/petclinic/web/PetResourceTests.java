package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PetResource.class)
public class PetResourceTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ClinicService clinicService;

	@Test
	public void shouldGetAPetInJSonFormat() throws Exception {

		Pet pet = setupPet();

		given(clinicService.findPetById(2)).willReturn(pet);

		mvc.perform(get("/owners/2/pets/2").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Basil"))
				.andExpect(jsonPath("$.type.id").value(6));
	}

	private Pet setupPet() {
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Bush");

		Pet pet = new Pet();

		pet.setName("Basil");
		pet.setId(2);

		PetType petType = new PetType();
		petType.setId(6);
		pet.setType(petType);

		owner.addPet(pet);
		return pet;
	}
}
