package io.platformbuilders.cliente.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;
import io.platformbuilders.cliente.infra.jpa.repository.ClienteJpaRepository;
import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;
import io.platformbuilders.cliente.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
class AlterarClienteUseCaseIT {

	@Autowired
	private AlterarClienteUseCase useCase;

	@Autowired
	private ClienteJpaRepository repository;

	@Test
	void deveAlterarClienteExistente() {
		final var jpa = repository.save(TestUtils.getClienteJpa());

		var request = TestUtils.getAlteraClienteRequest("44343434");
		request.setId(jpa.getId());
		final var response = useCase.alterar(request);

		assertNotNull(response);
		assertFalse(response.isNovo());
		validar(request, response.getClienteResponse());
	}

	@Test
	void deveIncluirClienteNaoCadastrado() {
		var request = TestUtils.getAlteraClienteRequest();
		request.setId(UUID.randomUUID().toString());
		final var response = useCase.alterar(request);

		assertNotNull(response);
		assertTrue(response.isNovo());
		validar(request, response.getClienteResponse());
	}

	@Test
	void deveLancarErroAoInformarCpfJaExistente() {
		repository.save(new ClienteJpa(UUID.randomUUID().toString(), "Maria Clara", "000000", "009988778899",
				LocalDate.of(1987, Month.AUGUST, 13), "999999999"));

		var request = new AlteraClienteRequest("Joao Maria dos Santos", "22222", "009988778899",
				LocalDate.of(1987, Month.JULY, 28), null);
		request.setId(UUID.randomUUID().toString());
		final var exception = assertThrows(BusinessException.class, () -> useCase.alterar(request));

		assertNotNull(exception);
		assertEquals("Já existe outro cliente cadastrado com esse CPF", exception.getMessage());
	}

	private void validar(AlteraClienteRequest request, ClienteResponse response) {
		assertEquals(request.getId(), response.getId());
		assertEquals(request.getNome(), response.getNome());
		assertEquals(request.getCpf(), response.getCpf());
		assertEquals(request.getRg(), response.getRg());
		assertEquals(request.getDataNascimento(), response.getDataNascimento());
		assertEquals(request.getTelefone(), response.getTelefone());
	}
}
