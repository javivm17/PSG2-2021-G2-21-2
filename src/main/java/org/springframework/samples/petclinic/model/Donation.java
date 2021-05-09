package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="donations")
public class Donation extends BaseEntity{

	//atributos
	@Column(name = "donation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	
	@Column(name = "amount")
	@NotNull
	@Min(value=0)
	private Integer amount;
	
	
	//relaciones
	@JoinColumn(name = "cause_id")
	@ManyToOne
	private Cause cause;
	
	@JoinColumn(name = "owner_id")
	@ManyToOne
	private Owner owner;

	
	public LocalDate getDate() {
		return this.date;
	}

	
	public void setDate(final LocalDate date) {
		this.date = date;
	}

	
	public Integer getAmount() {
		return this.amount;
	}

	
	public void setAmount(final Integer amount) {
		this.amount = amount;
	}

	
	public Cause getCause() {
		return this.cause;
	}

	
	public void setCause(final Cause cause) {
		this.cause = cause;
	}


	
	public Owner getOwner() {
		return this.owner;
	}


	
	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	

	
	
	
}
