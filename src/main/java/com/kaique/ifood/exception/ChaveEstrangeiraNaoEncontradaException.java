package com.kaique.ifood.exception;

public class ChaveEstrangeiraNaoEncontradaException extends NegocioException{
	private static final long serialVersionUID = 1L;

	public ChaveEstrangeiraNaoEncontradaException(String msg) {
		super(msg);
	}

	public ChaveEstrangeiraNaoEncontradaException(String nome , Long id) {
		this(String.format("Não foi possível encontrar a chave estrangeira de '%s', ID %d.", nome, id));
	}
}
