package io.platformbuilders.cliente.usecase;

import org.springframework.stereotype.Component;

import io.platformbuilders.cliente.domain.dto.mapper.CadastraClienteDtoMapper;
import io.platformbuilders.cliente.domain.gateway.ClienteGateway;
import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;
import io.platformbuilders.cliente.usecase.response.mapper.ClienteResponseMapper;

@Component
public class CadastrarClienteUseCaseImpl implements CadastrarClienteUseCase {

	private final ClienteGateway clienteGateway;

	public CadastrarClienteUseCaseImpl(final ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}

	@Override
	public ClienteResponse cadastrar(CadastraClienteRequest request) {
		validar(request);
		var dto = CadastraClienteDtoMapper.toDto(request);
		var cliente = clienteGateway.salvar(dto);
		return ClienteResponseMapper.toResponse(cliente);
	}

	private void validar(CadastraClienteRequest request) {
		if (clienteGateway.jaCadastrado(request.getCpf())) {
			throw new BusinessException("Já existe um cliente cadastrado com esse CPF");
		}
	}

}
