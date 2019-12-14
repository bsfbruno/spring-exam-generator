package br.com.bruno.examgenerate.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Professor extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field name cannot be empty")
	private String name;
	@Email(message = "This email is not valid")
	@NotEmpty(message = "The field name cannot be empty")
	@Column(unique = true)
	private String email;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
