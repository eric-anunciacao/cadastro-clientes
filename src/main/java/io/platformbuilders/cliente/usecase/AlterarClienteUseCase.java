package io.platformbuilders.cliente.usecase;

import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.response.AlteraClienteResponse;

public interface AlterarClienteUseCase {

	AlteraClienteResponse alterar(AlteraClienteRequest request);

}
