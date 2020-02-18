package br.com.bruno.examgenerate.endpoint.v1.deleteservice;

import org.springframework.stereotype.Service;

import br.com.bruno.examgenerate.persistence.repository.AssignmentRepository;
import br.com.bruno.examgenerate.persistence.repository.ChoiceRepository;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.examgenerate.persistence.repository.QuestionRepository;

@Service
public class CascadeDeleteService {

	private final QuestionRepository questionRepository;
	private final ChoiceRepository choiceRepository;
	private final CourseRepository courseRepository;
	private final AssignmentRepository assignmentRepository;
	
	public CascadeDeleteService(QuestionRepository questionRepository, ChoiceRepository choiceRepository,
			CourseRepository courseRepository, AssignmentRepository assignmentRepository) {
		super();
		this.questionRepository = questionRepository;
		this.choiceRepository = choiceRepository;
		this.courseRepository = courseRepository;
		this.assignmentRepository = assignmentRepository;
	}
	
	public void deleteCourseAndAllRelatedEntities(long courseId) {
		courseRepository.deleteById(courseId);
		questionRepository.deleteAllQuestionsRelatedToCourseId(courseId);
		choiceRepository.deleteAllChoicesRelatedToCourse(courseId);
		assignmentRepository.deleteAllAssignmentsRelatedToCourseId(courseId);
	}

	public void deleteQuestionAndAllRelatedEntities(long questionId) {
		questionRepository.deleteById(questionId);
		choiceRepository.deleteAllChoicesRelatedToQuestion(questionId);
	}
}
