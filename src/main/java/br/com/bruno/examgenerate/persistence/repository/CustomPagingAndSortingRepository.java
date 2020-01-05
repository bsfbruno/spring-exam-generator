package br.com.bruno.examgenerate.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.examgenerate.persistence.model.AbstractEntity;
import br.com.bruno.examgenerate.persistence.model.Course;

@NoRepositoryBean
public interface CustomPagingAndSortingRepository<T extends AbstractEntity, ID extends Long>
		extends PagingAndSortingRepository<T, ID>{

	@Override
	default <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Query("select e from #{#entityName} e where e.id = ?1 and e.professor = ?#{principal.professor} and e.enabled = true")
	T findOne(long id);

	default boolean existsById(Long id) {
		return findOne(id) != null;
	}

	@Override
	@Query("select e from #{#entityName} e where e.professor = ?#{principal.professor} and e.enabled = true")
	Iterable<T> findAll();

	@Override
	@Query("select e from #{#entityName} e where e.professor = ?#{principal.professor} and e.enabled = true")
	Iterable<T> findAllById(Iterable<ID> ids);

	@Override
	@Query("select count(e) from #{#entityName} e where e.professor = ?#{principal.professor} and e.enabled = true")
	long count();

	@Override
	@Transactional
	@Modifying
	@Query("update #{#entityName} e set e.enabled = false where e.id = ?1 and e.professor = ?#{principal.professor}")
	void deleteById(Long id);

	@Override
	default void delete(T entity) {
		deleteById(entity.getId());		
	}

	@Override
	@Transactional
	@Modifying
	default void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(entity -> deleteById(entity.getId()));		
	}

	@Override
	@Transactional
	@Query("update #{#entityName} e set e.enabled = false where e.professor = ?#{principal.professor}")
	void deleteAll();

	@Override
	@Query("select e from #{#entityName} e where e.professor = ?#{principal.professor} and e.enabled = true")
	Iterable<T> findAll(Sort sort);

	@Override
	@Query("select e from #{#entityName} e where e.professor = ?#{principal.professor} and e.enabled = true")
	Page<T> findAll(Pageable pageable);
}
