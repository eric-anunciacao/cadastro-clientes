package io.platformbuilders.cliente.utils;

import java.time.LocalDate;
import java.time.Month;

import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;
import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;
import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;

public class TestUtils {

	private TestUtils() {
	}

	public static CadastraClienteRequest getCadastraClienteRequest() {
		return new CadastraClienteRequest("Joao da Silva", "1111", "11111111111", LocalDate.of(1970, Month.JANUARY, 31),
				"(11) 99999-9999");
	}

	public static AlteraClienteRequest getAlteraClienteRequest() {
		return getAlteraClienteRequest("10293840567");
	}

	public static AlteraClienteRequest getAlteraClienteRequest(String cpf) {
		return new AlteraClienteRequest("Joao Maria dos Santos", "22222", cpf, LocalDate.of(1987, Month.JULY, 28),
				null);
	}

	public static ClienteJpa getClienteJpa() {
		return new ClienteJpa("6ad9914c-6aca-47ea-b67f-0e94678559d9", "Maria Clara", "000000", "98765432109",
				LocalDate.of(1987, Month.AUGUST, 13), "999999999");
	}

	public static AtualizaClienteRequest getAtualizaClienteRequest() {
		return new AtualizaClienteRequest("Atualizacao de nome", null, null, null, null);
	}

}
