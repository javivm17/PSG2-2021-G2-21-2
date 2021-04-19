package org.springframework.samples.petclinic.model;

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
	
	@NotNull
	private boolean isClosed;
	
	@NotBlank
	@Min(value=0)
	private Integer target;
	
	@NotBlank
	@Min(value=0)
	private Integer donated;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	

}
