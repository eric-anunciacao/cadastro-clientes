package io.platformbuilders.cliente.entrypoint.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import io.platformbuilders.cliente.usecase.response.AlteraClienteResponse;
import io.platformbuilders.cliente.utils.TestUtils;

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
		final var response = controller.consultar("Joao da Silva", "11111111111", "1234567", "1980-01-01",
				Pageable.unpaged());

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deveRetornarNoContentAoConsultarCliente() {
		BDDMockito.given(consultarClienteUseCase.consultar(Mockito.any()))
				.willThrow(new NoContentException(MENSAGEM_ERRO_BUSCA));

		final var exception = assertThrows(NoContentException.class,
				() -> controller.consultar("Maria", null, null, null, null));

		validar(exception);
	}

	@Test
	void deveConsultarClientePorId() {
		final var response = controller.consultarPor("21323123123");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deveRetornarNoContentAoBuscarApontamentoPorId() {
		BDDMockito.given(consultarClientePorIdUseCase.consultarPor(Mockito.anyString()))
				.willThrow(new NoContentException(MENSAGEM_ERRO_BUSCA));

		final var exception = assertThrows(NoContentException.class, () -> controller.consultarPor("987"));

		validar(exception);
	}

	@Test
	void deveCadastrarComSucesso() {
		final var response = controller.cadastrar(TestUtils.getCadastraClienteRequest());

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void deveCriarClienteComSucesso() {
		BDDMockito.given(alterarClienteUseCase.alterar(Mockito.any()))
				.willReturn(new AlteraClienteResponse(true, null));

		final var response = controller.alterar("123", TestUtils.getAlteraClienteRequest());

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void deveAlterarClienteComSucesso() {
		BDDMockito.given(alterarClienteUseCase.alterar(Mockito.any()))
				.willReturn(new AlteraClienteResponse(false, null));

		final var response = controller.alterar("123", TestUtils.getAlteraClienteRequest());

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void deveAtualizarClienteComSucesso() {
		final var response = controller.atualizar("321", TestUtils.getAtualizaClienteRequest());

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	private void validar(NoContentException exception) {
		assertNotNull(exception);
		assertEquals(MENSAGEM_ERRO_BUSCA, exception.getMessage());
	}

}
