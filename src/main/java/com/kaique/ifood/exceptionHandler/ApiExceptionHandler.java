package com.kaique.ifood.exceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		String ErrorManager = ((HttpStatus) statusCode).getReasonPhrase();

		if (body == null) {
			body = ApiErro.builder().Status(statusCode.value()).title(ErrorManager).build();
		} else if (body instanceof String) {
			body = ApiErro.builder().Status(statusCode.value()).title((String) body).build();

		}

		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> trataNegocioException(NegocioException e, WebRequest request) {

		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> trataEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.Status(HttpStatus.NOT_FOUND.value())
				.type(ProblemType.ENTIDADE_NAO_ENCONTRADA.getUrl())
				.title(ProblemType.ENTIDADE_NAO_ENCONTRADA.getTitle())
				.detail(e.getMessage())
				.build();
		
		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> trataEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
