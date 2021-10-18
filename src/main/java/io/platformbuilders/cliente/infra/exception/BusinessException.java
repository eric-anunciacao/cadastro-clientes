package io.platformbuilders.cliente.infra.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6993878570229158267L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

}
