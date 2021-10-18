package io.platformbuilders.cliente.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

import io.platformbuilders.cliente.infra.exception.NoContentException;
import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;
import io.platformbuilders.cliente.infra.jpa.repository.ClienteJpaRepository;
import io.platformbuilders.cliente.usecase.request.ConsultaClienteRequest;
import io.platformbuilders.cliente.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
class ConsultarClienteUseCaseIT {

	@Autowired
	private ConsultarClienteUseCase useCase;

	@Autowired
	private ClienteJpaRepository repository;

	private ClienteJpa clienteJpa;

	@BeforeEach
	void init() {
		clienteJpa = repository.save(TestUtils.getClienteJpa());
	}

	@Test
	void deveConsultarTodosClientes() {
		deveConsultarClientesPor(null, null, null, null);
	}

	@Test
	void deveConsultarClientePorNome() {
		deveConsultarClientesPor(clienteJpa.getNome(), null, null, null);
	}

	@Test
	void deveConsultarClientePorCpf() {
		deveConsultarClientesPor(null, clienteJpa.getCpf(), null, null);
	}

	@Test
	void deveConsultarClientePorRg() {
		deveConsultarClientesPor(null, null, clienteJpa.getRg(), null);
	}

	@Test
	void deveConsultarClientePorDataNascimento() {
		deveConsultarClientesPor(null, null, null, clienteJpa.getDataNascimento().toString());
	}

	private void deveConsultarClientesPor(String nome, String cpf, String rg, String dataNascimento) {
		final var request = new ConsultaClienteRequest(nome, cpf, rg, dataNascimento, Pageable.unpaged());
		final var responses = useCase.consultar(request);

		assertNotNull(responses);
		assertFalse(CollectionUtils.isEmpty(responses));
	}

	@Test
	void deveRetornarErroAoNaoEncontrarClientePorNome() {
		deveRetornarErroAoBuscarClientePor("Cliente nao encontrado");
	}

	@Test
	void deveRetornarErroAoNaoEncontrarCliente() {
		repository.deleteAll();
		deveRetornarErroAoBuscarClientePor(null);
	}

	private void deveRetornarErroAoBuscarClientePor(String nome) {
		final var request = new ConsultaClienteRequest(nome, null, null, null, Pageable.unpaged());
		final var exception = assertThrows(NoContentException.class, () -> useCase.consultar(request));

		assertNotNull(exception);
		assertEquals("Nenhum cliente cadastrado na base de dados", exception.getMessage());
	}

}
