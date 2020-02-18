package br.com.bruno.examgenerate.endpoint.v1.choice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bruno.examgenerate.SpringJsfApplication;
import br.com.bruno.examgenerate.endpoint.v1.ProfessorEndpointTest;
import br.com.bruno.examgenerate.endpoint.v1.question.QuestionEndpointTest;
import br.com.bruno.examgenerate.persistence.model.Choice;
import br.com.bruno.examgenerate.persistence.model.Question;
import br.com.bruno.examgenerate.persistence.repository.ChoiceRepository;
import br.com.bruno.examgenerate.persistence.repository.QuestionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringJsfApplication.class)
@AutoConfigureMockMvc
public class ChoiceEndpointTest {

	@MockBean
	private QuestionRepository questionRepository;
	
	@MockBean
	private ChoiceRepository choiceRepository;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	private HttpEntity<Void> professorHeader;
	private HttpEntity<Void> wrongHeader;
	private Choice choiceAnswerFalse = mockChoiceFalse();
	private Choice choiceAnswerTrue = mockChoiceTrue();

	private static Choice mockChoiceFalse() {
		return Choice.newChoice()
				.withId(1L)
				.withTitle("Is a room?")
				.withQuestion(QuestionEndpointTest.mockQuestion())
				.withCorrectAnswer(false)
				.withProfessor(ProfessorEndpointTest.mockProfessor())
				.build();
	}
	
	private static Choice mockChoiceTrue() {
		return Choice.newChoice()
				.withId(2L)
				.withTitle("Is a template?")
				.withQuestion(QuestionEndpointTest.mockQuestion())
				.withCorrectAnswer(true)
				.withProfessor(ProfessorEndpointTest.mockProfessor())
				.build();
	}
	
	@Before
	public void configProfessorHeader() {
		String body = "{\"username\":\"bruno\",\"password\":\"senhamestre\"}";
		HttpHeaders headers = testRestTemplate.postForEntity("/login", body, String.class).getHeaders();
		this.professorHeader = new HttpEntity<>(headers);
	}

	@Before
	public void configWrongHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "1111");
		this.wrongHeader = new HttpEntity<>(headers);
	}
	
	@Before
	public void setup() {
		BDDMockito.when(choiceRepository.findOne(choiceAnswerFalse.getId())).thenReturn(choiceAnswerFalse);
		BDDMockito.when(choiceRepository.findOne(choiceAnswerTrue.getId())).thenReturn(choiceAnswerTrue);
		BDDMockito.when(choiceRepository.listChoiceByQuestionId(choiceAnswerTrue.getQuestion().getId()))
				.thenReturn(Arrays.asList(choiceAnswerFalse, choiceAnswerTrue));
		BDDMockito.when(questionRepository.findOne(choiceAnswerTrue.getQuestion().getId()))
				.thenReturn(choiceAnswerTrue.getQuestion());
	}
	
	@Test
	public void getChoiceByIdWhenTokenIsWrongShouldReturn403() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/1", HttpMethod.GET,
				wrongHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void listChoicesByQuestionIdWhenTokenIsWrongShouldReturn403() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/list/1", HttpMethod.GET,
				wrongHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void listChoicesByQuestionIdWhenQuestionIdDoesNotExistsShouldReturnEmptyList() throws Exception {
		ResponseEntity<List<Choice>> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/list/99/",
				HttpMethod.GET, professorHeader, new ParameterizedTypeReference<List<Choice>>() {
				});
		assertThat(exchange.getBody()).isEmpty();
	}

	@Test
	public void listChoicesByQuestionIdWhenQuestionIdExistsShouldReturn200() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/list/1/?title=what",
				HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getChoiceByIdWithoutIdShouldReturn400() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/", HttpMethod.GET,
				professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void getChoiceByIdWhenChoiceDoesNotExistsShouldReturn404() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/-1", HttpMethod.GET,
				professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void getChoiceByIdWhenChoiceIdExistsShouldReturn200() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/1", HttpMethod.GET,
				professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteChoinceWhenIdExistsShouldReturn200() throws Exception {
		long id = 1L;
		BDDMockito.doNothing().when(choiceRepository).deleteById(id);
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/{id}", HttpMethod.DELETE,
				professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteChoiceWhenIdDoesNotExistsShouldReturn404() throws Exception {
		long id = -1L;
		BDDMockito.doNothing().when(choiceRepository).deleteById(id);
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/question/choice/{id}", HttpMethod.DELETE,
				professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void createChoiceWhenTitleIsNullShouldReturn400() throws Exception {
		Choice choice = choiceRepository.findOne(1L);
		choice.setTitle(null);
		assertThat(createChoice(choice).getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void createChoiceWhenQuestionDoesNotExistsShouldReturn404() throws Exception {
		Choice choice = choiceRepository.findOne(1L);
		choice.setQuestion(new Question());
		assertThat(createChoice(choice).getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void createChoiceWhenEverythingIsRightShouldReturn200() throws Exception {
		Choice choice = choiceRepository.findOne(1L);
		choice.setId(null);
		assertThat(createChoice(choice).getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void createChoiceWithCorrectAnswerTrueShouldTriggerUpdateChoiceAndCreate() throws Exception {
		Choice choice = choiceRepository.findOne(2L);
		choice.setId(null);
		createChoice(choice);
		BDDMockito.verify(choiceRepository, Mockito.times(1)).save(choice);
		BDDMockito.verify(choiceRepository, Mockito.times(1)).updateAllOtherChoicesCorrectAnswerToFalse(choice, choice.getQuestion());
	}

	private ResponseEntity<String> createChoice(Choice choice) {
		BDDMockito.when(choiceRepository.save(choice)).thenReturn(choice);
		return testRestTemplate.exchange("/v1/professor/course/question/choice/", HttpMethod.POST,
				new HttpEntity<>(choice, professorHeader.getHeaders()), String.class);
	}
	
}
