 package br.com.bruno.examgenerate.endpoint.v1.assignment;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.examgenerate.endpoint.v1.genericservice.GenericService;
import br.com.bruno.examgenerate.persistence.model.Assignment;
import br.com.bruno.examgenerate.persistence.model.Question;
import br.com.bruno.examgenerate.persistence.repository.AssignmentRepository;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.examgenerate.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("v1/professor/course/question/assignment")
@Api(description = "Operations related to courses' assignment")
public class AssignmentEndpoint {

	private final AssignmentRepository assignmentRepository;
	private final GenericService genericService;
	private final EndpointUtil endpointUtil;
	private final CourseRepository courseRepository;
	
	@Autowired
	public AssignmentEndpoint(AssignmentRepository assignmentRepository, GenericService genericService,
			EndpointUtil endpointUtil, CourseRepository courseRepository) {
		this.assignmentRepository = assignmentRepository;
		this.genericService = genericService;
		this.endpointUtil = endpointUtil;
		this.courseRepository = courseRepository;
	}
	
	@ApiOperation(value = "Return an assignment based on it's id", response = Question.class)
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getAssignmentnById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(assignmentRepository.findOne(id));
	}
	
	@ApiOperation(value = "Return a list of assignments related to course", response = Assignment[].class)
	@GetMapping(path = "list/{coursetId}/")
	public ResponseEntity<?> listQuestion(
			@PathVariable long courseId,
			@ApiParam("Assignment title") @RequestParam(value = "title", defaultValue = "") String title) {
		return new ResponseEntity<>(assignmentRepository.listAssignmentsByCourseAndTitle(courseId, title), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete a specific assignment and return 200 ok with no body")
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		validateAssignmentExistenceOnDB(id);
		assignmentRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Update assignment and return 200 ok with no body")
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Assignment assignment) {
		validateAssignmentExistenceOnDB(assignment.getId());
		assignmentRepository.save(assignment);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void validateAssignmentExistenceOnDB(Long id) {
		genericService.courseNotFound(id, assignmentRepository, "Question not found");
	}
	
	@ApiOperation(value = "Create assignment and return the assignment created")
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Assignment assignment) {
		genericService.courseNotFound(assignment.getCourse(), courseRepository, "Course not found");
		assignment.setProfessor(endpointUtil.extractProfessorFromToken());
		return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.OK);
	}
}
