package br.com.bruno.examgenerate.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.bruno.examgenerate.persistence.model.Assignment;

public interface AssignmentRepository extends CustomPagingAndSortingRepository<Assignment, Long>{
		
	@Query("select a from Assignment a where a.course.id = ?1 and a.title like %?2% and a.professor = ?#{principal.professor} and a.enabled = true")
	List<Assignment> listAssignmentsByCourseAndTitle(long id, String title);
	
	@Query("update Assignment a set a.enabled = false where a.course.id = ?1 and a.professor = ?#{principal.professor} and a.enabled = true")
	@Modifying
	void deleteAllAssignmentsRelatedToCourseId(long courseId);
}
