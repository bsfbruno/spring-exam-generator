package br.com.bruno.examgenerate.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class ApplicationUser extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field name cannot be empty")
	@Column(unique = true)
	private String username;
	@NotEmpty(message = "The field name cannot be empty")
	private String password;
	@OneToOne
	private Professor professor;
	
	public ApplicationUser() {
		
	}
	
	public ApplicationUser(ApplicationUser applicationUser) {
		super();
		this.username = applicationUser.username;
		this.password = applicationUser.password;
		this.professor = applicationUser.professor;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
}
