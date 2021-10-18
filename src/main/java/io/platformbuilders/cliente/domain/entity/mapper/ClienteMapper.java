package io.platformbuilders.cliente.domain.entity.mapper;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import io.platformbuilders.cliente.domain.entity.Cliente;
import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;

public class ClienteMapper {

	private ClienteMapper() {
	}

	public static Set<Cliente> toClientes(Page<ClienteJpa> page) {
		if (page == null || page.isEmpty()) {
			return Collections.emptySet();
		}

		return page.stream().map(ClienteMapper::toCliente).collect(Collectors.toSet());
	}

	public static Optional<Cliente> toCliente(Optional<ClienteJpa> optional) {
		if (optional.isEmpty()) {
			return Optional.empty();
		}

		return Optional.ofNullable(toCliente(optional.get()));
	}

	public static Cliente toCliente(ClienteJpa jpa) {
		if (jpa == null) {
			return null;
		}

		return new Cliente(jpa.getId(), jpa.getNome(), jpa.getRg(), jpa.getCpf(), jpa.getDataNascimento(),
				jpa.getTelefone());
	}

}
