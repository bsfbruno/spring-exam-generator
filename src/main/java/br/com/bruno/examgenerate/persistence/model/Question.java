package br.com.bruno.examgenerate.persistence.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Question extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field title cannot be empty")
	@ApiModelProperty(notes = "The title of the question")
	private String title;
	@ManyToOne
	private Course course;
	@ManyToOne
	private Professor professor;

		
	public Question() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	@Generated("SparkTools")
	private Question(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.course = builder.course;
		this.professor = builder.professor;
	}

	/**
	 * Creates builder to build {@link Question}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder newQuestion() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Question}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private String title;
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

		public Builder withCourse(Course course) {
			this.course = course;
			return this;
		}

		public Builder withProfessor(Professor professor) {
			this.professor = professor;
			return this;
		}

		public Question build() {
			return new Question(this);
		}
	}
}
