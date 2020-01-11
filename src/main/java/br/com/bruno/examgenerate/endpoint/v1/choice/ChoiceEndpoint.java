package br.com.bruno.examgenerate.endpoint.v1.choice;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.examgenerate.endpoint.v1.genericservice.GenericService;
import br.com.bruno.examgenerate.persistence.model.Choice;
import br.com.bruno.examgenerate.persistence.repository.ChoiceRepository;
import br.com.bruno.examgenerate.persistence.repository.QuestionRepository;
import br.com.bruno.examgenerate.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/professor/course/question/choice")
@Api(description = "Operation related to questions' choice")
public class ChoiceEndpoint {

	private final EndpointUtil endpointUtil;
	private final GenericService genericService;
	private final ChoiceRepository choiceRepository;
	private final QuestionRepository questionRepository;

	@Autowired
	public ChoiceEndpoint(EndpointUtil endpointUtil, GenericService genericService,
			ChoiceRepository choiceRepository, QuestionRepository questionRepository) {
		this.endpointUtil = endpointUtil;
		this.genericService = genericService;
		this.choiceRepository = choiceRepository;
		this.questionRepository = questionRepository;
	}

	@ApiOperation(value = "Return a list of choices related to the questionId", response = Choice[].class)
	@GetMapping(path = "list/{questionId}/")
	public ResponseEntity<?> listChoicesByQuestionId(@PathVariable long questionId) {
		return new ResponseEntity<>(choiceRepository.listChoiceByQuestionId(questionId), HttpStatus.OK);
	}

	@ApiOperation(value = "Create choice and return the choice created", 
				  notes = "If this choice's correctAnswer is true all the other related to this question will be update to false")
	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@Valid @RequestBody Choice choice) {
		validateChoicesQuestion(choice);
		choice.setProfessor(endpointUtil.extractProfessorFromToken());
		Choice savedChoice = choiceRepository.save(choice);
		updateChangingOtherChoicesCorrectAnswerToFalse(choice);
		return new ResponseEntity<>(savedChoice, HttpStatus.OK);
	}

	@ApiOperation(value = "Update choice and return 200 Ok with no body", 
				  notes = "If this choice's correctAnswer is true all the other related to this question will be update to false")
	@PutMapping
	@Transactional
	public ResponseEntity<?> update(@Valid @RequestBody Choice choice) {
		validateChoicesQuestion(choice);
		updateChangingOtherChoicesCorrectAnswerToFalse(choice);
		choiceRepository.save(choice);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete a specific choice and return 200 Ok with no body")
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		genericService.courseNotFound(id, choiceRepository, "Choice was not found");
		choiceRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void validateChoicesQuestion(@Valid @RequestBody Choice choice) {
		genericService.courseNotFound(choice.getQuestion(), questionRepository, "Question not found");
	}

	private void updateChangingOtherChoicesCorrectAnswerToFalse(Choice choice) {
		if (choice.isCorrectAnswer())
			choiceRepository.updateAllOtherChoicesCorrectAnswerToFalse(choice, choice.getQuestion());
	}
}
