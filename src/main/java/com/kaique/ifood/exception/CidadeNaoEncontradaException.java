package com.kaique.ifood.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}

	public CidadeNaoEncontradaException(Long id) {
		this(String.format("Não existe Cidade com id %d", id));
	}
}
