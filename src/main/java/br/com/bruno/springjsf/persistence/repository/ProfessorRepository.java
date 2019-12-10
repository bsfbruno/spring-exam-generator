package br.com.bruno.springjsf.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.bruno.springjsf.persistence.model.Professor;

public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long>{

	Professor findByEmail(String email);
}
