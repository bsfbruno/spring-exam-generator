package br.com.bruno.examgenerate.endpoint.v1.course;

import java.util.Collections;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bruno.examgenerate.endpoint.v1.ProfessorEndpointTest;
import br.com.bruno.examgenerate.persistence.model.Course;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.examgenerate.persistence.repository.ProfessorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CourseEndpointTest {
	
	@MockBean
	private CourseRepository courseRepository;
	
	@MockBean
	private ProfessorRepository professorRepository;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	//As we are using SpringSecurity We need this variables below
	private HttpEntity<Void> professorHeader;
	private HttpEntity<Void> wrongHeader;
	private Course course = mockCourse();
	
	private static Course mockCourse() {
		return Course.newCourse()
				.withId(1L)
				.withName("Java")
				.withProfessor(ProfessorEndpointTest.mockProfessor())
				.build();
	}
	
	//creating headers
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
		this.professorHeader = new HttpEntity<>(headers);
	}
	
	//every time a method is invoked a response must be set up
	@Before
	public void setup() {
		BDDMockito.when(courseRepository.findOne(course.getId())).thenReturn(course);
		BDDMockito.when(courseRepository.listCourses("")).thenReturn(Collections.singletonList(course));
		BDDMockito.when(courseRepository.listCourses("java")).thenReturn(Collections.singletonList(course));
	}
}
