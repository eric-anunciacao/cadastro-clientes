package io.platformbuilders.cliente.usecase;

import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;

public interface AlterarClienteUseCase {

	ClienteResponse alterar(AlteraClienteRequest request);

}
