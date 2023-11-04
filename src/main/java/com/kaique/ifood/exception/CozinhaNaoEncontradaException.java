package com.kaique.ifood.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String msg) {
		super(msg);
	}

	public CozinhaNaoEncontradaException(Long id) {
		this(String.format("Não existe Cozinha com id %d", id));
	}
}
