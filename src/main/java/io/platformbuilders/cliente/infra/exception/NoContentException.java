package io.platformbuilders.cliente.infra.exception;

public class NoContentException extends RuntimeException {

	private static final long serialVersionUID = -1202371962613201083L;

	public NoContentException(String msg) {
		super(msg);
	}

}
