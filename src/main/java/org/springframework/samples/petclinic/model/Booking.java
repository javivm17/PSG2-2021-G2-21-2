package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="bookings")
public class Booking extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@Column(name = "initial_date")			
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate initialDate;
	
	@Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
	
}
