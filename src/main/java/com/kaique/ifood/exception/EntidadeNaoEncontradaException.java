package com.kaique.ifood.exception;

public class EntidadeNaoEncontradaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String msg) {
		super(msg);
	}
}
