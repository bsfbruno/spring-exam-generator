package br.com.bruno.examgenerate.persistence.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Course extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field name cannot be empty")
	@ApiModelProperty(notes = "The name of the course")
	private String name;
	@ManyToOne(optional = false)
	private Professor professor;
	@Column(columnDefinition = "boolean default true")
	private boolean enabled = true;

	@Generated("SparkTools")
	private Course(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.professor = builder.professor;
		this.enabled = builder.enabled;
	}
	
	public Course() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Creates builder to build {@link Course}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder newCourse() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Course}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private String name;
		private Professor professor;
		private boolean enabled;

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

		public Builder withProfessor(Professor professor) {
			this.professor = professor;
			return this;
		}

		public Builder withEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Course build() {
			return new Course(this);
		}
	}	
}
