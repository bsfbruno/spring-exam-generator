package br.com.bruno.examgenerate.endpoint.v1.deleteservice;

import org.springframework.stereotype.Service;

import br.com.bruno.examgenerate.persistence.repository.ChoiceRepository;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.examgenerate.persistence.repository.QuestionRepository;

@Service
public class CascadeDeleteService {

	private final QuestionRepository questionRepository;
	private final ChoiceRepository choiceRepository;
	private final CourseRepository courseRepository;
	
	public CascadeDeleteService(QuestionRepository questionRepository, ChoiceRepository choiceRepository,
			CourseRepository courseRepository) {
		super();
		this.questionRepository = questionRepository;
		this.choiceRepository = choiceRepository;
		this.courseRepository = courseRepository;
	}
	
	public void cascadeDeleteCourseQuestionAndChoice(long courseId) {
		courseRepository.deleteById(courseId);
		questionRepository.deleteAllQuestionsRelatedToCourseId(courseId);
		choiceRepository.deleteAllChoicesRelatedToCourse(courseId);
	}

	public void cascadeDeleteQuestionAndChoice(long questionId) {
		questionRepository.deleteById(questionId);
		choiceRepository.deleteAllChoicesRelatedToQuestion(questionId);
	}
}
