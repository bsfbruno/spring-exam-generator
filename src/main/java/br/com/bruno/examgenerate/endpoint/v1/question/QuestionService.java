package br.com.bruno.examgenerate.endpoint.v1.question;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.examgenerate.exception.ResouceNotFoundException;
import br.com.bruno.examgenerate.persistence.model.Question;
import br.com.bruno.examgenerate.persistence.repository.QuestionRepository;

@Service
public class QuestionService implements Serializable{

	private static final long serialVersionUID = 1L;

	private final QuestionRepository questionRepository;
	
	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	
	public void throwResourceNotFoundExceptionDoesNotExist(Question question) {
		if (question == null || question.getId() == null || questionRepository.findOne(question.getId()) == null)
			throw new ResouceNotFoundException("Question not found");
	}
	
	public void throwResourceNotFoundIfQuestionDoesNotExist(long id) {
		if (id == 0 || questionRepository.findOne(id) == null)
			throw new ResouceNotFoundException("Question not found");
	}
}
