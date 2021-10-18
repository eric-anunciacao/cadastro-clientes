package io.platformbuilders.cliente.usecase;

import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;

public interface AtualizarClienteUseCase {

	ClienteResponse atualizar(AtualizaClienteRequest request);

}
