package br.com.bruno.examgenerate.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.annotation.Generated;

@Entity
public class Professor extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field name cannot be empty")
	private String name;
	@Email(message = "This email is not valid")
	@NotEmpty(message = "The field name cannot be empty")
	@Column(unique = true)
	private String email;
	
	public Professor() {
		
	}

	@Generated("SparkTools")
	private Professor(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.email = builder.email;
	}
			
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
	/**
	 * Creates builder to build {@link Professor}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder newProfessor() {
		return new Builder();
	}
	/**
	 * Builder to build {@link Professor}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private String name;
		private String email;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Professor build() {
			return new Professor(this);
		}
	}
}
