package br.com.bruno.examgenerate.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Generated;

@Entity
public class Assignment extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field title cannot be empty")
	@ApiModelProperty(notes = "The title of the assignment")
	private String title;
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne(optional = false)
	private Course course;
	@ManyToOne(optional = false)
	private Professor professor;
	
	public Assignment() {
	}

	@Generated("SparkTools")
	private Assignment(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.createdAt = builder.createdAt;
		this.course = builder.course;
		this.professor = builder.professor;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	/**
	 * Creates builder to build {@link Assignment}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder newAssignment() {
		return new Builder();
	}
	/**
	 * Builder to build {@link Assignment}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private String title;
		private LocalDateTime createdAt;
		private Course course;
		private Professor professor;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder withCourse(Course course) {
			this.course = course;
			return this;
		}

		public Builder withProfessor(Professor professor) {
			this.professor = professor;
			return this;
		}

		public Assignment build() {
			return new Assignment(this);
		}
	}
}
