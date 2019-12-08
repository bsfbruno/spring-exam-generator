package br.com.bruno.springjsf.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.bruno.springjsf.persistence.model.ApplicationUser;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

	ApplicationUser findByUsername(String username);
}
