package com.kaique.ifood.exceptionHandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.kaique.ifood.exception.ChaveEstrangeiraNaoEncontradaException;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.exception.NegocioException;

import ch.qos.logback.core.status.Status;

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

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		if (rootCause instanceof IgnoredPropertyException) {
			return tratarPropertyBindingException((IgnoredPropertyException) rootCause, headers, status, request);
		}

		if (rootCause instanceof UnrecognizedPropertyException) {
			return tratarUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status,
					request);
		}

		ApiErro erro = ApiErro.builder().Status(status.value()).type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail("O corpo da requisição esta inválido. verifique o erro de sintaxe").build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> tratarUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ApiErro erro = ApiErro.builder().Status(status.value()).type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail(String.format("O campo '%s' não existe, por gentileza verificar", path)).build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> tratarPropertyBindingException(IgnoredPropertyException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ApiErro erro = ApiErro.builder().Status(status.value()).type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail(String.format("O campo '%s' está sendo ignorado e não deve ser enviado na requisição.", path))
				.build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ApiErro erro = ApiErro.builder().Status(status.value()).type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail(String.format(
						"A propriedade '%s' recebeu o valor '%s' , que é de um tipo inválido. corrija e informe um valor compativel com o tipo %s. ",
						path, ex.getValue(), ex.getTargetType().getSimpleName()))
				.build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> trataMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
			WebRequest request) {

		ApiErro erro = ApiErro.builder().Status(HttpStatus.BAD_REQUEST.value())
				.type(ProblemType.PARAMETRO_INVALIDO.getUrl()).title(ProblemType.PARAMETRO_INVALIDO.getTitle())
				.detail(String.format(
						"O parâmetro de URL '%s' recebeu um valor '%s' que é do tipo inválido, por gentileza informe um valor do tipo '%s' ",
						e.getName(), e.getValue(), e.getRequiredType().getSimpleName()))
				.build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> trataNegocioException(NegocioException e, WebRequest request) {

		ApiErro erro = ApiErro.builder().Status(HttpStatus.CONFLICT.value()).type(ProblemType.NEGOCIO.getUrl())
				.title(ProblemType.NEGOCIO.getTitle()).detail(e.getMessage()).build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ChaveEstrangeiraNaoEncontradaException.class)
	public ResponseEntity<?> trataChaveEstrangeiraNaoEncontradaException(ChaveEstrangeiraNaoEncontradaException e,
			WebRequest request) {

		ApiErro erro = ApiErro.builder().Status(HttpStatus.BAD_REQUEST.value())
				.type(ProblemType.CHAVE_ESTRANGEIRA_NAO_ENCONTRA.getUrl())
				.title(ProblemType.CHAVE_ESTRANGEIRA_NAO_ENCONTRA.getTitle()).detail(e.getMessage()).build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> trataEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {

		ApiErro erro = ApiErro.builder().Status(HttpStatus.NOT_FOUND.value())
				.type(ProblemType.ENTIDADE_NAO_ENCONTRADA.getUrl())
				.title(ProblemType.ENTIDADE_NAO_ENCONTRADA.getTitle()).detail(e.getMessage()).build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> trataEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

		ApiErro erro = ApiErro.builder().Status(HttpStatus.CONFLICT.value()).type(ProblemType.ENTIDADE_EM_USO.getUrl())
				.title(ProblemType.ENTIDADE_EM_USO.getTitle()).detail(e.getMessage()).build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
