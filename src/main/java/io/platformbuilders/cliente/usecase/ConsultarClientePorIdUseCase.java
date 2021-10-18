package io.platformbuilders.cliente.usecase;

import io.platformbuilders.cliente.usecase.response.ClienteResponse;

public interface ConsultarClientePorIdUseCase {

	ClienteResponse consultarPor(String id);

}
