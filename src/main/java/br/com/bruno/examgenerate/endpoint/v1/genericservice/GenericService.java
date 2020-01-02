package br.com.bruno.examgenerate.endpoint.v1.genericservice;

import org.springframework.stereotype.Service;

import br.com.bruno.examgenerate.exception.ResouceNotFoundException;
import br.com.bruno.examgenerate.persistence.model.AbstractEntity;
import br.com.bruno.examgenerate.persistence.repository.CustomPagingAndSortingRepository;

@Service
public class GenericService {

	public <T extends AbstractEntity, ID extends Long> void courseNotFound(T t, CustomPagingAndSortingRepository<T, ID> repository, String msg) {
		if (t == null || t.getId() == null || repository.findOne(t.getId()) == null)
				throw new ResouceNotFoundException(msg);
	}

	public <T extends AbstractEntity, ID extends Long> void courseNotFound(long id, CustomPagingAndSortingRepository<T, ID> repository, String msg) {
		if (id == 0 || repository.findOne(id) == null)
				throw new ResouceNotFoundException(msg);
	}
}
