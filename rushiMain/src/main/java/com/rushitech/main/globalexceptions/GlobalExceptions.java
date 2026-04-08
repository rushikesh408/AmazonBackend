package com.rushitech.main.globalexceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleUserExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		Map<String, Object> responseErrorMap = new HashMap<String, Object>();
		responseErrorMap.put("result", "failed");
		responseErrorMap.put("errors", errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorMap);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("result", "failed");
		responseMap.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);

	}

}
