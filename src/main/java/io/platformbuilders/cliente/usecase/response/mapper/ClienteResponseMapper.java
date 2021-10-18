package io.platformbuilders.cliente.usecase.response.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import io.platformbuilders.cliente.domain.entity.Cliente;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;

public class ClienteResponseMapper {

	private ClienteResponseMapper() {
	}

	public static Set<ClienteResponse> toResponses(Set<Cliente> clientes) {
		if (CollectionUtils.isEmpty(clientes)) {
			return Collections.emptySet();
		}

		return clientes.stream().map(ClienteResponseMapper::toResponse).collect(Collectors.toSet());
	}

	public static ClienteResponse toResponse(Cliente cliente) {
		if (cliente == null) {
			return null;
		}

		return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getRg(), cliente.getCpf(),
				cliente.getDataNascimento(), cliente.getIdade(), cliente.getTelefone());
	}

}
