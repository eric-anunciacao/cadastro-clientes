package io.platformbuilders.cliente.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.platformbuilders.cliente.infra.exception.NoContentException;
import io.platformbuilders.cliente.infra.jpa.repository.ClienteJpaRepository;
import io.platformbuilders.cliente.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
class ConsultarClientePorIdUseCaseIT {

	@Autowired
	private ConsultarClientePorIdUseCase useCase;

	@Autowired
	private ClienteJpaRepository repository;

	@Test
	void deveConsultarCliente() {
		final var jpa = repository.save(TestUtils.getClienteJpa());

		final var response = useCase.consultarPor(jpa.getId());

		assertNotNull(response);
		assertEquals(jpa.getId(), response.getId());
		assertEquals(jpa.getNome(), response.getNome());
		assertEquals(jpa.getCpf(), response.getCpf());
		assertEquals(jpa.getRg(), response.getRg());
		assertEquals(jpa.getDataNascimento(), response.getDataNascimento());
		assertEquals(jpa.getTelefone(), response.getTelefone());
	}

	@Test
	void deveLancarErroAoNaoEncontrarCliente() {
		final var exception = assertThrows(NoContentException.class,
				() -> useCase.consultarPor(UUID.randomUUID().toString()));

		assertNotNull(exception);
		assertEquals("Cliente não encontrado para o código informado", exception.getMessage());
	}

}
