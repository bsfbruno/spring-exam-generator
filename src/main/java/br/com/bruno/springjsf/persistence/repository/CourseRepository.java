package br.com.bruno.springjsf.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.bruno.springjsf.persistence.model.Course;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long>{
	
	@Query("select c from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
	Course findById(long id);
}
