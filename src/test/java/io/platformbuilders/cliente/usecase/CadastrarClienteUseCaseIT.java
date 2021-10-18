package io.platformbuilders.cliente.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;
import io.platformbuilders.cliente.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
class CadastrarClienteUseCaseIT {

	@Autowired
	private CadastrarClienteUseCase useCase;

	@Test
	void deveCadastrarCliente() {
		final var request = TestUtils.getCadastraClienteRequest();

		final var response = useCase.cadastrar(request);

		assertNotNull(response);
		assertNotNull(response.getId());
		assertEquals(request.getNome(), response.getNome());
		assertEquals(request.getCpf(), response.getCpf());
		assertEquals(request.getRg(), response.getRg());
		assertEquals(request.getDataNascimento(), response.getDataNascimento());
		assertEquals(request.getTelefone(), response.getTelefone());
	}

	@Test
	void deveRetornarErroAoCadastrarClienteComMesmoCpf() {
		useCase.cadastrar(
				new CadastraClienteRequest("Joao", "9999", "22222222222", LocalDate.of(1980, Month.JANUARY, 01), null));

		final var exception = assertThrows(BusinessException.class,
				() -> useCase.cadastrar(new CadastraClienteRequest("Maria", "1234", "22222222222",
						LocalDate.of(1999, Month.JANUARY, 31), null)));

		assertNotNull(exception);
		assertEquals("Já existe um cliente cadastrado com esse CPF", exception.getMessage());
	}

}
