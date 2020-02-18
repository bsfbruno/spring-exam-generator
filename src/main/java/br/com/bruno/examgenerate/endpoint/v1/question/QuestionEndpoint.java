 package br.com.bruno.examgenerate.endpoint.v1.question;

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
import br.com.bruno.examgenerate.persistence.model.Question;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.examgenerate.persistence.repository.QuestionRepository;
import br.com.bruno.examgenerate.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("v1/professor/course/question")
@Api(description = "Operations related to courses' question")
public class QuestionEndpoint {

	private final QuestionRepository questionRepository;
	private final GenericService genericService;
	private final EndpointUtil endpointUtil;
	private final CourseRepository courseRepository;
	private final CascadeDeleteService cascadeDeleteService;
	
	@Autowired
	public QuestionEndpoint(QuestionRepository questionRepository, GenericService genericService,
			EndpointUtil endpointUtil, CourseRepository courseRepository, CascadeDeleteService cascadeDeleteService) {
		this.questionRepository = questionRepository;
		this.genericService = genericService;
		this.endpointUtil = endpointUtil;
		this.courseRepository = courseRepository;
		this.cascadeDeleteService = cascadeDeleteService;
	}
	
	@ApiOperation(value = "Return a question based on it's id", response = Question.class)
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getQuestionById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(questionRepository.findOne(id));
	}
	
	@ApiOperation(value = "Return a list of question related to course", response = Question[].class)
	@GetMapping(path = "list/{courseId}/")
	public ResponseEntity<?> listQuestion(
			@PathVariable long courseId,
			@ApiParam("Question title") @RequestParam(value = "title", defaultValue = "") String title) {
		return new ResponseEntity<>(questionRepository.listQuestionsByCourseAndTitle(courseId, title), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete a specific question and all related choices and return 200 ok with no body")
	@DeleteMapping(path = "{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable long id) {
		validateQuestionExistenceOnDB(id);
		cascadeDeleteService.deleteQuestionAndAllRelatedEntities(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Update question and return 200 ok with no body")
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Question question) {
		validateQuestionExistenceOnDB(question.getId());
		questionRepository.save(question);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void validateQuestionExistenceOnDB(Long id) {
		genericService.courseNotFound(id, questionRepository, "Question not found");
	}
	
	@ApiOperation(value = "Create question and return the question created")
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Question question) {
		question.getCourse().getId();
		genericService.courseNotFound(question.getCourse(), courseRepository, "Course not found");
		question.setProfessor(endpointUtil.extractProfessorFromToken());
		return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
	}
}
