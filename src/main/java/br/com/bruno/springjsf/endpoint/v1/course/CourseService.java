package br.com.bruno.springjsf.endpoint.v1.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.springjsf.exception.ResouceNotFoundException;
import br.com.bruno.springjsf.persistence.model.Course;
import br.com.bruno.springjsf.persistence.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;
	
	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	public void throwResourceNotFoundCourse(Course course) {
		if (course == null || course.getId() == null || courseRepository.findOne(course.getId()) == null) {
			throw new ResouceNotFoundException("Course not found");
		}
	}
}
