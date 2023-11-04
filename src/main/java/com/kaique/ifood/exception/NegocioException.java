package com.kaique.ifood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NegocioException(String msg) {
		super(msg);
	}

	public NegocioException(String msg , Throwable e) {
		super(msg , e);
	}
}
