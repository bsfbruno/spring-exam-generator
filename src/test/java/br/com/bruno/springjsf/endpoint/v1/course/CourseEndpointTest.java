package br.com.bruno.springjsf.endpoint.v1.course;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bruno.examgenerate.SpringJsfApplication;
import br.com.bruno.examgenerate.persistence.model.Course;
import br.com.bruno.examgenerate.persistence.repository.CourseRepository;
import br.com.bruno.springjsf.endpoint.v1.ProfessorEndpointTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringJsfApplication.class)
@AutoConfigureMockMvc
public class CourseEndpointTest {
	
	@MockBean
	private CourseRepository courseRepository;
		
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
		this.wrongHeader = new HttpEntity<>(headers);
	}
	
	//every time a method is invoked a response must be set up
	@Before
	public void setup() {
		BDDMockito.when(courseRepository.findOne(course.getId())).thenReturn(course);
		BDDMockito.when(courseRepository.listCourses("")).thenReturn(Collections.singletonList(course));
		BDDMockito.when(courseRepository.listCourses("java")).thenReturn(Collections.singletonList(course));
	}
	
	@Test
	public void getCourseByIdWhenTokenIsWrongShouldReturn403() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/1", HttpMethod.GET, wrongHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void listCourseWhenTokenIsWrongShouldReturn403() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/list?name=", HttpMethod.GET, wrongHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void listAllCoursesWhenNameDoesNotExistsShouldReturn404() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/list?name=userDoesNotExist", HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void listAllCoursesWhenNameExistsShouldReturn200() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/list?name=java", HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void getCourseByIdWithoutIdShouldReturn400() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/", HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void getCourseByIdWhenCourseDoesNotExistsShouldReturn404() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/-1", HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void listAllCoursesWhenCourseExistsShouldReturn200() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/1", HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deleteCourseWhenIdExistsShouldReturn200() throws Exception {
		long id = 1L;
		BDDMockito.doNothing().when(courseRepository).deleteById(id);
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/{id}", HttpMethod.GET, professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deleteCourseWhenIdDoesNotExistsShouldReturn404() throws Exception {
		long id = -1L;
		BDDMockito.doNothing().when(courseRepository).deleteById(id);
		ResponseEntity<String> exchange = testRestTemplate
				.exchange("/v1/professor/course/{id}", HttpMethod.GET, professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void createCourseWhenNameIsNullShouldReturn400() throws Exception {
		Course course = courseRepository.findOne(1L);
		course.setName(null);
		assertThat(createCourse(course).getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void createCourseWhenEverythingIsRightShouldReturn200() throws Exception {
		Course course = courseRepository.findOne(1L);
		course.setId(null);
		assertThat(createCourse(course).getStatusCodeValue()).isEqualTo(200);
	}
	
	private ResponseEntity<String> createCourse(Course course) {
		BDDMockito.when(courseRepository.save(course)).thenReturn(course);
		return testRestTemplate.exchange("/v1/professor/course/", HttpMethod.POST,
				new HttpEntity<>(course, professorHeader.getHeaders()), String.class);
	}
	
	
}
