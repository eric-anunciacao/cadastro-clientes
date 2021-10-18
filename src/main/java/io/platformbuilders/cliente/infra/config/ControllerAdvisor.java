package io.platformbuilders.cliente.infra.config;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.infra.exception.NoContentException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		return handle(HttpStatus.UNPROCESSABLE_ENTITY, request, Arrays.asList(ex.getMessage()));
	}

	@ExceptionHandler(UnexpectedTypeException.class)
	protected ResponseEntity<Object> handleUnexpectedTypeException(UnexpectedTypeException ex, WebRequest request) {
		return handle(HttpStatus.UNPROCESSABLE_ENTITY, request, Arrays.asList(ex.getMessage()));
	}

	@ExceptionHandler(NoContentException.class)
	protected ResponseEntity<Object> handleNoContentException(NoContentException ex, WebRequest request) {
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(SQLException.class)
	protected ResponseEntity<Object> handleSQLException(SQLException ex, WebRequest request) {
		return handle(HttpStatus.INTERNAL_SERVER_ERROR, request, Arrays.asList(ex.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var errors = ex.getBindingResult().getFieldErrors().stream()
				.map(x -> x.getField() + ": " + x.getDefaultMessage()).collect(Collectors.toList());

		return handle(status, request, errors);
	}

	private ResponseEntity<Object> handle(HttpStatus status, WebRequest request, List<String> errors) {
		var body = new LinkedHashMap<String, Object>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());
		body.put("errors", errors);

		if (request != null) {
			body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
		}

		return new ResponseEntity<>(body, status);
	}

}