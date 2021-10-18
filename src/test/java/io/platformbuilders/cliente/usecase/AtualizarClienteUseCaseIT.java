package io.platformbuilders.cliente.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;
import io.platformbuilders.cliente.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
class AtualizarClienteUseCaseIT {

	@Autowired
	private AtualizarClienteUseCase useCase;

	@Autowired
	private ClienteJpaRepository repository;

	@Test
	void deveAtualizarCliente() {
		final var jpa = repository.save(TestUtils.getClienteJpa());

		var request = TestUtils.getAtualizaClienteRequest();
		request.setId(jpa.getId());
		final var response = useCase.atualizar(request);

		assertNotNull(response);
		assertEquals(jpa.getId(), response.getId());
		assertEquals(request.getNome(), response.getNome());
		assertEquals(jpa.getCpf(), response.getCpf());
		assertEquals(jpa.getRg(), response.getRg());
		assertEquals(jpa.getDataNascimento(), response.getDataNascimento());
		assertEquals(jpa.getTelefone(), response.getTelefone());
	}

	@Test
	void deveLancarErroDeCodigoNaoEncontrado() {
		var request = TestUtils.getAtualizaClienteRequest();
		request.setId(UUID.randomUUID().toString());
		final var exception = assertThrows(BusinessException.class, () -> useCase.atualizar(request));

		validarErro(exception, "Cliente não encontrado para o código informado");
	}

	@Test
	void deveLancarErroDeCpfJaCadastrado() {
		final var cpf = "3312331209";
		repository.save(new ClienteJpa(UUID.randomUUID().toString(), "Carlos Alberto", "000000", cpf,
				LocalDate.of(1987, Month.AUGUST, 13), "999999999"));

		final var jpa = repository.save(TestUtils.getClienteJpa());
		var request = new AtualizaClienteRequest("Atualizacao de nome", null, cpf, null, null);
		request.setId(jpa.getId());
		final var exception = assertThrows(BusinessException.class, () -> useCase.atualizar(request));

		validarErro(exception, "Já existe outro cliente cadastrado com esse CPF");
	}

	private void validarErro(BusinessException exception, String mensagem) {
		assertNotNull(exception);
		assertEquals(mensagem, exception.getMessage());
	}
}
