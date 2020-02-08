package br.com.bruno.examgenerate.endpoint.v1.course;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.examgenerate.endpoint.v1.deleteservice.CascadeDeleteService;
import br.com.bruno.examgenerate.endpoint.v1.genericservice.GenericService;
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
	private final GenericService genericService;
	private final CascadeDeleteService cascadeDeleteService;

	@Autowired
	public CourseEndpoint(CourseRepository courseRepository, EndpointUtil endpointUtil, 
			GenericService genericService, CascadeDeleteService cascadeDeleteService) {
		this.courseRepository = courseRepository;
		this.endpointUtil = endpointUtil;
		this.genericService = genericService;
		this.cascadeDeleteService = cascadeDeleteService;
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
		return endpointUtil.returnObjectOrNotFound(courseRepository.listCoursesByName(name));
	}
	
	@ApiOperation(value = "Delete a specific course and all related question and choices and return 200 Ok with no body", response = Course.class)
	@DeleteMapping(path = "{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable long id) {
		validatecourseExistenceOnDB(id);
		cascadeDeleteService.cascadeDeleteCourseQuestionAndChoice(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update course and return 200 Ok with no body")
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Course course) {
		validatecourseExistenceOnDB(course.getId());
		courseRepository.save(course);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void validatecourseExistenceOnDB(Long id) {
		genericService.courseNotFound(id, courseRepository, "Course not found");		
	}
	
	@ApiOperation(value = "Create course and return the course created")
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Course course) {
		course.setProfessor(endpointUtil.extractProfessorFromToken());
		return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
	}
}
