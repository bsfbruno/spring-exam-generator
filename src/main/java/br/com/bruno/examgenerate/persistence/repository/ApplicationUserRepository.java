package br.com.bruno.examgenerate.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.bruno.examgenerate.persistence.model.ApplicationUser;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

	ApplicationUser findByUsername(String username);
}
