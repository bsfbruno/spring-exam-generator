package br.com.bruno.examgenerate.persistence.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Choice extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The field title cannot be empty")
	@ApiModelProperty(notes = "The title of the choice")
	private String title;
	
	@NotNull(message = "The field correctAnswer must be true or false")
	@ApiModelProperty(notes = "Correct answer for the associated question, you can have only one correct answer per question")
	private boolean correctAnswer;
	
	@ManyToOne(optional = false)
	private Question question;
	
	@ManyToOne(optional = false)
	private Professor professor;
	
	public Choice() {
		
	}

	@Generated("SparkTools")
	private Choice(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.correctAnswer = builder.correctAnswer;
		this.question = builder.question;
		this.professor = builder.professor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 * Creates builder to build {@link Choice}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder newChoice() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Choice}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private String title;
		private boolean correctAnswer;
		private Question question;
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

		public Builder withCorrectAnswer(boolean correctAnswer) {
			this.correctAnswer = correctAnswer;
			return this;
		}

		public Builder withQuestion(Question question) {
			this.question = question;
			return this;
		}

		public Builder withProfessor(Professor professor) {
			this.professor = professor;
			return this;
		}

		public Choice build() {
			return new Choice(this);
		}
	}
}
