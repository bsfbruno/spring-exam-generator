package br.com.bruno.springjsf.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.springjsf.persistence.model.Course;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long>{
	
	@Query("select c from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
	Course findOne(long id);
	
	@Query("select c from Course c where c.name like %?1% and c.professor = ?#{principal.professor}")
	List<Course> listCourses(String name);
	
	@Query("delete from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
	@Modifying
	@Transactional
	void delete(Long id);	
	
	@Query("delete from Course c where c = ?1 and c.professor = ?#{principal.professor}")
	void delete(Course course);
	
	@Query("select c from Course c where c = ?1 and c.professor = ?#{principal.professor}")
	Course findOne(Course course);
}
