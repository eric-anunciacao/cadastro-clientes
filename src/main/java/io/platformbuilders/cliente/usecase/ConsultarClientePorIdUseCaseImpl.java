package io.platformbuilders.cliente.usecase;

import org.springframework.stereotype.Component;

import io.platformbuilders.cliente.domain.gateway.ClienteGateway;
import io.platformbuilders.cliente.infra.exception.NoContentException;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;
import io.platformbuilders.cliente.usecase.response.mapper.ClienteResponseMapper;

@Component
public class ConsultarClientePorIdUseCaseImpl implements ConsultarClientePorIdUseCase {

	private final ClienteGateway clienteGateway;

	public ConsultarClientePorIdUseCaseImpl(final ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}

	@Override
	public ClienteResponse consultarPor(String id) {
		var optional = clienteGateway.consultarPor(id);

		if (optional.isPresent()) {
			return ClienteResponseMapper.toResponse(optional.get());
		} else {
			throw new NoContentException("Cliente não encontrado para o código informado");
		}
	}

}
