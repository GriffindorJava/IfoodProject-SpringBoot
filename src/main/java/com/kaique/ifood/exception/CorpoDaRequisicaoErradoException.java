package com.kaique.ifood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CorpoDaRequisicaoErradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CorpoDaRequisicaoErradoException(String msg){
		super(msg);
	}
}
