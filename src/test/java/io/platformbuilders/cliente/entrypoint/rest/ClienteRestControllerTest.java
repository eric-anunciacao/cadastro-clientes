package io.platformbuilders.cliente.entrypoint.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import io.platformbuilders.cliente.infra.exception.NoContentException;
import io.platformbuilders.cliente.usecase.AlterarClienteUseCase;
import io.platformbuilders.cliente.usecase.AtualizarClienteUseCase;
import io.platformbuilders.cliente.usecase.CadastrarClienteUseCase;
import io.platformbuilders.cliente.usecase.ConsultarClientePorIdUseCase;
import io.platformbuilders.cliente.usecase.ConsultarClienteUseCase;
import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;
import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;

@ExtendWith(MockitoExtension.class)
class ClienteRestControllerTest {

	private static final String MENSAGEM_ERRO_BUSCA = "Não foi encontrado nenhum cliente cadastrado";

	@InjectMocks
	private ClienteRestController controller;

	@Mock
	private CadastrarClienteUseCase cadastrarClienteUseCase;

	@Mock
	private ConsultarClienteUseCase consultarClienteUseCase;

	@Mock
	private AlterarClienteUseCase alterarClienteUseCase;

	@Mock
	private ConsultarClientePorIdUseCase consultarClientePorIdUseCase;

	@Mock
	private AtualizarClienteUseCase atualizarClienteUseCase;

	@Test
	void deveConsultarCliente() {
		var response = controller.consultar("Joao da Silva", "11111111111", "1234567", "1980-01-01",
				Pageable.unpaged());

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deveRetornarNoContentAoConsultarCliente() {
		BDDMockito.given(consultarClienteUseCase.consultar(Mockito.any()))
				.willThrow(new NoContentException(MENSAGEM_ERRO_BUSCA));

		var exception = assertThrows(NoContentException.class,
				() -> controller.consultar("Maria", null, null, null, null));

		validar(exception);
	}

	@Test
	void deveConsultarClientePorId() {
		var response = controller.consultarPor("21323123123");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deveRetornarNoContentAoBuscarApontamentoPorId() {
		BDDMockito.given(consultarClientePorIdUseCase.consultarPor(Mockito.anyString()))
				.willThrow(new NoContentException(MENSAGEM_ERRO_BUSCA));

		var exception = assertThrows(NoContentException.class, () -> controller.consultarPor("987"));

		validar(exception);
	}

	@Test
	void deveCadastrarComSucesso() {
		var response = controller.cadastrar(new CadastraClienteRequest("Paulo", "12345", "12345678909",
				LocalDate.of(1987, Month.JULY, 31), "(11) 99999-9999"));

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void deveAlterarClienteComSucesso() {
		var response = controller.alterar("123", new AlteraClienteRequest("Nome alteracao", "00000", "99999999999",
				LocalDate.of(1999, Month.AUGUST, 18), null));

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void deveAtualizarClienteComSucesso() {
		var response = controller.atualizar("321",
				new AtualizaClienteRequest("Atualizacao de nome", null, null, null, null));

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	private void validar(NoContentException exception) {
		assertNotNull(exception);
		assertEquals(MENSAGEM_ERRO_BUSCA, exception.getMessage());
	}

}
