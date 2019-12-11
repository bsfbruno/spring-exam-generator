package br.com.bruno.springjsf.util;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EndpointUtil implements Serializable{

	private static final long serialVersionUID = 1L;

	public ResponseEntity<?> returnObjectOrNotFound(Object object) {
		return object == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(object, HttpStatus.OK);
	}
}
