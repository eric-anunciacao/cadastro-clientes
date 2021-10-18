package io.platformbuilders.cliente.usecase;

import org.springframework.stereotype.Component;

import io.platformbuilders.cliente.domain.dto.mapper.AlteraClienteDtoMapper;
import io.platformbuilders.cliente.domain.gateway.ClienteGateway;
import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.response.AlteraClienteResponse;
import io.platformbuilders.cliente.usecase.response.mapper.ClienteResponseMapper;

@Component
public class AlterarClienteUseCaseImpl implements AlterarClienteUseCase {

	private final ClienteGateway clienteGateway;

	public AlterarClienteUseCaseImpl(final ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}

	@Override
	public AlteraClienteResponse alterar(AlteraClienteRequest request) {
		var novo = isNovoRegistro(request.getId());

		validar(request);

		var dto = AlteraClienteDtoMapper.toDto(request);
		var cliente = clienteGateway.alterar(dto);
		var response = ClienteResponseMapper.toResponse(cliente);

		return new AlteraClienteResponse(novo, response);
	}

	private boolean isNovoRegistro(String id) {
		var optional = clienteGateway.consultarPor(id);
		return optional.isEmpty();
	}

	private void validar(AlteraClienteRequest request) {
		var optional = clienteGateway.consultarPorCpf(request.getCpf());

		if (optional.isPresent() && !optional.get().getId().equals(request.getId())) {
			throw new BusinessException("Já existe outro cliente cadastrado com esse CPF");
		}
	}
}
