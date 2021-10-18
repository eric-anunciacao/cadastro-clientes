package io.platformbuilders.cliente.usecase;

import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;
import io.platformbuilders.cliente.usecase.response.ClienteResponse;

public interface CadastrarClienteUseCase {

	ClienteResponse salvar(CadastraClienteRequest request);

}
