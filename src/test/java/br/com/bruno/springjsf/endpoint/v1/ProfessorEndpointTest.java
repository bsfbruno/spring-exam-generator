package br.com.bruno.springjsf.endpoint.v1;

import br.com.bruno.examgenerate.persistence.model.Professor;

public class ProfessorEndpointTest {

	public static Professor mockProfessor() {
		return Professor.newProfessor()
				.withId(1L)
				.withName("Bruno")
				.withEmail("brunosuportefs@gmail.com")
				.build();
	}
}
