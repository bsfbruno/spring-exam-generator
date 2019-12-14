package br.com.bruno.examgenerate.endpoint.v1.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.examgenerate.exception.ResouceNotFoundException;
import br.com.bruno.examgenerate.persistence.model.Course;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;
	
	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	public void throwResourceNotFoundCourse(long courseId) {
		if (courseId == 0 || courseRepository.findOne(courseId) == null) {
			throw new ResouceNotFoundException("Course not found");
		}
	}
	
	public void throwResourceNotFoundCourse(Course course) {
		if (course == null || course.getId() == null || courseRepository.findOne(course.getId()) == null) {
			throw new ResouceNotFoundException("Course not found");
		}
	}
}
