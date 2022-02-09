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

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@RestController
public class PetResource extends AbstractResourceController {

	private final ClinicService clinicService;

	@Autowired
	public PetResource(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@GetMapping("/petTypes")
	Collection<PetType> getPetTypes() {
		return clinicService.findPetTypes();
	}

	@PostMapping("/owners/{ownerId}/pets")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void processCreationForm(
			@RequestBody final PetRequest petRequest,
			@PathVariable("ownerId") final int ownerId) {

		Pet pet = new Pet();
		Owner owner = clinicService.findOwnerById(ownerId);
		owner.addPet(pet);

		save(pet, petRequest);
	}

	@PutMapping("/owners/{ownerId}/pets/{petId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void processUpdateForm(@RequestBody final PetRequest petRequest) {
		save(clinicService.findPetById(petRequest.getId()), petRequest);
	}

	private void save(final Pet pet, final PetRequest petRequest) {

		pet.setName(petRequest.getName());
		pet.setBirthDate(petRequest.getBirthDate());

		for (PetType petType : clinicService.findPetTypes()) {
			if (petType.getId() == petRequest.getTypeId()) {
				pet.setType(petType);
			}
		}

		clinicService.savePet(pet);
	}

	@GetMapping("/owners/*/pets/{petId}")
	public PetDetails findPet(@PathVariable("petId") final int petId) {
		Pet pet = clinicService.findPetById(petId);
		return new PetDetails(pet);
	}

	static class PetRequest {

		private int id;

		public Integer getId() {
			return id;
		}

		@JsonFormat(pattern = "yyyy-MM-dd")
		Date birthDate;

		@Size(min = 1)
		String name;

		int typeId;

		public void setId(final int id) {
			this.id = id;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(final Date birthDate) {
			this.birthDate = birthDate;
		}

		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public int getTypeId() {
			return typeId;
		}

		public void setTypeId(final int typeId) {
			this.typeId = typeId;
		}
	}

	static class PetDetails {
		private int id;

		public Integer getId() {
			return id;
		}

		String name;

		String owner;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		Date birthDate;

		PetType type;

		PetDetails() {
		}

		PetDetails(final Pet pet) {
			id = pet.getId();
			name = pet.getName();
			owner = pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName();
			birthDate = pet.getBirthDate();
			type = pet.getType();
		}

		public PetRequest map(final Class<PetRequest> destinationClass) {
			PetRequest request = new PetRequest();
			request.setId(id);
			request.setBirthDate(birthDate);
			request.setName(name);
			request.setTypeId(type.getId());
			return request;
		}

		public String getName() {
			return name;
		}

		public String getOwner() {
			return owner;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public PetType getType() {
			return type;
		}
	}

}
