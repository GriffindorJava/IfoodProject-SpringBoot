package com.kaique.ifood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException(String msg) {
		super(msg);
	}

	public EntidadeEmUsoException(Long id) {
		this(String.format("O código %d não pode ser apagado, pois está relacionado com outra tabela", id));
	}
}
