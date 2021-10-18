package io.platformbuilders.cliente.usecase;

import org.springframework.stereotype.Component;

import io.platformbuilders.cliente.domain.dto.mapper.AlteraClienteDtoMapper;
import io.platformbuilders.cliente.domain.entity.Cliente;
import io.platformbuilders.cliente.domain.gateway.ClienteGateway;
import io.platformbuilders.cliente.infra.exception.BusinessException;
import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;
import io.platformbuilders.cliente.usecase.response.mapper.ClienteResponseMapper;

@Component
public class AtualizarClienteUseCaseImpl implements AtualizarClienteUseCase {

	private final ClienteGateway clienteGateway;

	public AtualizarClienteUseCaseImpl(final ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}

	@Override
	public ClienteResponse atualizar(AtualizaClienteRequest request) {
		var cliente = consultarCliente(request);

		validar(request);

		var dto = AlteraClienteDtoMapper.toDto(cliente, request);
		var clienteAlterado = clienteGateway.alterar(dto);
		return ClienteResponseMapper.toResponse(clienteAlterado);
	}

	private Cliente consultarCliente(AtualizaClienteRequest request) {
		var optional = clienteGateway.consultarPor(request.getId());

		if (optional.isEmpty()) {
			throw new BusinessException("Cliente não encontrado para o código informado");
		}

		return optional.get();
	}

	private void validar(AtualizaClienteRequest request) {
		var optional = clienteGateway.consultarPorCpf(request.getCpf());

		if (optional.isPresent() && !optional.get().getId().equals(request.getId())) {
			throw new BusinessException("Já existe outro cliente cadastrado com esse CPF");
		}
	}

}
