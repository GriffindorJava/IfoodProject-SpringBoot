package com.kaique.ifood.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String msg) {
		super(msg);
	}

	public EstadoNaoEncontradaException(Long id) {
		this(String.format("Não existe Estado com id %d", id));
	}
}
