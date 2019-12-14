package br.com.bruno.examgenerate.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EndpointUtil implements Serializable{

	private static final long serialVersionUID = 1L;

	public ResponseEntity<?> returnObjectOrNotFound(Object object) {
		return object == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(object, HttpStatus.OK);
	}
	
	public ResponseEntity<?> returnObjectOrNotFound(List<?> list) {
		return list == null || list.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(list, HttpStatus.OK);
	}
}
