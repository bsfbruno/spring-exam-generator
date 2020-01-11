package br.com.bruno.examgenerate.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.bruno.examgenerate.persistence.model.Choice;
import br.com.bruno.examgenerate.persistence.model.Question;

public interface ChoiceRepository extends CustomPagingAndSortingRepository<Choice, Long>{

	@Query("select c from Choice c where c.question.id = ?1 and c.professor = ?#{principal.professor} and c.enabled = true")
	List<Choice> listChoiceByQuestionId(long questionId);
	
	@Query("update Choice c set c.correctAnswer = false where c <> ?1 and c.question = ?2 and c.professor = ?#{principal.professor} and c.enabled = true")
	void updateAllOtherChoicesCorrectAnswerToFalse(Choice choice, Question question);
}
