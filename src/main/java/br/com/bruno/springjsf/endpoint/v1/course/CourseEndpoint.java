package br.com.bruno.springjsf.endpoint.v1.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.springjsf.persistence.repository.CourseRepository;
import br.com.bruno.springjsf.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/professor/course")
@Api(description = "Operations related to professor's course")
public class CourseEndpoint {

	private final CourseRepository courseRepository;
	private final EndpointUtil endpointUtil;

	@Autowired
	public CourseEndpoint(CourseRepository courseRepository, EndpointUtil endpointUtil) {
		this.courseRepository = courseRepository;
		this.endpointUtil = endpointUtil;
	}
	
	@ApiOperation(value = "Return a course based on its id")
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getCourseById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(courseRepository.findOne(id));
	}
}
