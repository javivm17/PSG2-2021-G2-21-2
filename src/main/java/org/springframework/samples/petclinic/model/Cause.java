package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "causes")
public class Cause extends NamedEntity{
	
	private String description;
	
	@NotBlank
	private String organization;
	
	@Column(name = "closed")
	private boolean isClosed;
	
	@NotNull
	@Min(value=0)
	private Integer target;
	
	private Integer donated;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(final String organization) {
		this.organization = organization;
	}

	public boolean isClosed() {
		return this.isClosed;
	}

	public void setClosed(final boolean isClosed) {
		System.out.println("ME CAGO EN LOS PUTOS ERRORES");
		this.isClosed = isClosed;
		System.out.println("ME CAGO EN LOS PUTOS ERRORES DEL COPON");
	}

	public Integer getTarget() {
		return this.target;
	}

	public void setTarget(final Integer target) {
		this.target = target;
	}

	
	public Integer getDonated() {
		return this.donated;
	}

	
	public void setDonated(final Integer donated) {
		this.donated = donated;
		if (this.donated >= this.target)
			this.setClosed(true);
	}

	

}
