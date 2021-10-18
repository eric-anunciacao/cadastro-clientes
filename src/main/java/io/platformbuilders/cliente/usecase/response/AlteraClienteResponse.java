package io.platformbuilders.cliente.usecase.response;

public class AlteraClienteResponse {

	private boolean novo;
	private ClienteResponse clienteResponse;

	public AlteraClienteResponse(boolean novo, ClienteResponse clienteResponse) {
		this.novo = novo;
		this.clienteResponse = clienteResponse;
	}

	public boolean isNovo() {
		return novo;
	}

	public ClienteResponse getClienteResponse() {
		return clienteResponse;
	}

}
