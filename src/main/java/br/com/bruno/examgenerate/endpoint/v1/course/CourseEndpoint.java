package br.com.bruno.examgenerate.endpoint.v1.course;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.examgenerate.persistence.model.Course;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.examgenerate.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("v1/professor/course")
@Api(description = "Operations related to professor's course")
public class CourseEndpoint {

	private final CourseRepository courseRepository;
	private final EndpointUtil endpointUtil;
	private final CourseService courseService;

	@Autowired
	public CourseEndpoint(CourseRepository courseRepository, EndpointUtil endpointUtil, CourseService courseService) {
		this.courseRepository = courseRepository;
		this.endpointUtil = endpointUtil;
		this.courseService = courseService;
	}
	
	@ApiOperation(value = "Return a course based on its id")
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getCourseById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(courseRepository.findOne(id));
	}
	
	@ApiOperation(value = "Return a list of courses related to professor")
	@GetMapping(path = "list")
	public ResponseEntity<?> listCourses(@ApiParam("Course name")
			@RequestParam(value = "name", defaultValue = "") String name) {
		return endpointUtil.returnObjectOrNotFound(courseRepository.listCourses(name));
	}
	
	@ApiOperation(value = "Delete a specific course and return 200 Ok with no body", response = Course.class)
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		courseService.throwResourceNotFoundCourse(id);
		courseRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update course and return 200 Ok with no body")
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Course course) {
		courseService.throwResourceNotFoundCourse(course);
		courseRepository.save(course);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
