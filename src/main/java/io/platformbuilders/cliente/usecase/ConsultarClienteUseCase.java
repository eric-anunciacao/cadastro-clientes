package io.platformbuilders.cliente.usecase;

import java.util.Set;

import io.platformbuilders.cliente.usecase.request.ConsultaClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;

public interface ConsultarClienteUseCase {

	Set<ClienteResponse> consultar(ConsultaClienteRequest request);

}
