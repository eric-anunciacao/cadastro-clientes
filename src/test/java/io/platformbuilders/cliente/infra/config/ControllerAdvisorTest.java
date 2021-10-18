package io.platformbuilders.cliente.infra.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.sql.SQLException;

import javax.validation.UnexpectedTypeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.infra.exception.NoContentException;

@ExtendWith(MockitoExtension.class)
class ControllerAdvisorTest {

	@Test
	void deveTratarBusinessException() {
		var advisor = new ControllerAdvisor();
		var exception = new BusinessException();

		var response = advisor.handleBusinessException(exception, null);

		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	@Test
	void deveTratarUnexpectedTypeException() {
		var advisor = new ControllerAdvisor();
		var exception = new UnexpectedTypeException();

		var response = advisor.handleUnexpectedTypeException(exception, null);

		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	@Test
	void deveTratarNoContentException() {
		var advisor = new ControllerAdvisor();
		var exception = new NoContentException("");

		var response = advisor.handleNoContentException(exception, null);

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void deveTratarSQLException() {
		var advisor = new ControllerAdvisor();
		var exception = new SQLException();

		var response = advisor.handleSQLException(exception, null);

		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void deveTratarMethodArgumentNotValidException() {
		var advisor = new ControllerAdvisor();
		var status = HttpStatus.BAD_GATEWAY;

		MethodParameter parameter = mock(MethodParameter.class);
		BindingResult bindingResult = mock(BindingResult.class);
		var exception = new MethodArgumentNotValidException(parameter, bindingResult);

		var response = advisor.handleMethodArgumentNotValid(exception, null, status, null);

		assertNotNull(response);
		assertEquals(status, response.getStatusCode());
	}

}