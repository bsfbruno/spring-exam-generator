package br.com.bruno.examgenerate.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.bruno.examgenerate.persistence.model.Question;

public interface QuestionRepository extends CustomPagingAndSortingRepository<Question, Long>{
		
	@Query("select q from Question q where q.course.id = ?1 and q.title like %?2% and q.professor = ?#{principal.professor} and q.enabled = true")
	List<Question> listQuestionsByCourseAndTitle(long id, String title);
}
