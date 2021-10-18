package io.platformbuilders.cliente.usecase;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.platformbuilders.cliente.domain.dto.mapper.ConsultaClienteDtoMapper;
import io.platformbuilders.cliente.domain.gateway.ClienteGateway;
import io.platformbuilders.cliente.infra.exception.NoContentException;
import io.platformbuilders.cliente.usecase.request.ConsultaClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;
import io.platformbuilders.cliente.usecase.response.mapper.ClienteResponseMapper;

@Component
public class ConsultarClienteUseCaseImpl implements ConsultarClienteUseCase {

	private final ClienteGateway clienteGateway;

	public ConsultarClienteUseCaseImpl(final ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}

	@Override
	public Set<ClienteResponse> consultar(ConsultaClienteRequest request) {
		var dto = ConsultaClienteDtoMapper.toDto(request);

		var clientes = clienteGateway.consultar(dto);

		if (CollectionUtils.isEmpty(clientes)) {
			throw new NoContentException("Nenhum cliente cadastrado na base de dados");
		}

		return ClienteResponseMapper.toResponses(clientes);
	}

}
