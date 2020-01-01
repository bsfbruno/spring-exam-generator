package br.com.bruno.examgenerate.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.bruno.examgenerate.persistence.model.Course;

public interface CourseRepository extends CustomPagingAndSortingRepository<Course, Long>{
		
	@Query("select c from Course c where c.name like %?1% and c.professor = ?#{principal.professor} and c.enabled = true")
	List<Course> listCoursesByName(String name);
}
