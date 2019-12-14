package br.com.bruno.examgenerate.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.bruno.examgenerate.persistence.model.Professor;

public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long>{

	Professor findByEmail(String email);
}
