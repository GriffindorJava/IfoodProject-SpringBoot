package com.kaique.ifood.exceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.kaique.ifood.exception.ChaveEstrangeiraNaoEncontradaException;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.exception.NegocioException;
import com.kaique.ifood.exceptionHandler.ApiErro.Field;

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
			return trataInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		if (rootCause instanceof IgnoredPropertyException) {
			return tratarPropertyBindingException((IgnoredPropertyException) rootCause, headers, status, request);
		}

		if (rootCause instanceof UnrecognizedPropertyException) {
			return tratarUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status,
					request);
		}

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value())
				.type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail("O corpo da requisição esta inválido. verifique o erro de sintaxe")
				.build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> tratarUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value())
				.type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail(String.format("O campo '%s' não existe, por gentileza verificar", path))
				.build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> tratarPropertyBindingException(IgnoredPropertyException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value()).type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail(String.format("O campo '%s' está sendo ignorado e não deve ser enviado na requisição.", path))
				.build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> trataInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value())
				.type(ProblemType.CORPO_ILEGIVEL.getUrl())
				.title(ProblemType.CORPO_ILEGIVEL.getTitle())
				.detail(String.format(
						"A propriedade '%s' recebeu o valor '%s' , que é de um tipo inválido. corrija e informe um valor compativel com o tipo %s. ",
						path, ex.getValue(), ex.getTargetType().getSimpleName()))
				.build();

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> trataException(Exception ex , WebRequest request) {
		
		ApiErro erro =  ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.type(ProblemType.ERRO_DE_SISTEMA.getUrl())
				.title(ProblemType.ERRO_DE_SISTEMA.getTitle())
				.detail("Desculpe, encontramos um problema inesperado em nosso sistema. Por favor, tente novamente e, "
						+ "se o erro persistir, entre em contato com o nosso suporte técnico para obter assistência.")
				.build();
		
		return handleExceptionInternal(ex, erro , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> trataMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
			WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(HttpStatus.NOT_FOUND.value())
				.type(ProblemType.PARAMETRO_INVALIDO.getUrl())
				.title(ProblemType.PARAMETRO_INVALIDO.getTitle())
				.detail(String.format(
						"O parâmetro de URL '%s' recebeu um valor '%s' que é do tipo inválido, por gentileza informe um valor do tipo '%s' ",
						e.getName(), e.getValue(), e.getRequiredType().getSimpleName()))
				.build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> trataNegocioException(NegocioException e, WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(HttpStatus.CONFLICT.value())
				.type(ProblemType.NEGOCIO.getUrl())
				.title(ProblemType.NEGOCIO.getTitle())
				.detail(e.getMessage())
				.build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ChaveEstrangeiraNaoEncontradaException.class)
	public ResponseEntity<?> trataChaveEstrangeiraNaoEncontradaException(ChaveEstrangeiraNaoEncontradaException e,
			WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(HttpStatus.BAD_REQUEST.value())
				.type(ProblemType.CHAVE_ESTRANGEIRA_NAO_ENCONTRA.getUrl())
				.title(ProblemType.CHAVE_ESTRANGEIRA_NAO_ENCONTRA.getTitle())
				.detail(e.getMessage())
				.build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> trataEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(HttpStatus.NOT_FOUND.value())
				.type(ProblemType.ENTIDADE_NAO_ENCONTRADA.getUrl())
				.title(ProblemType.ENTIDADE_NAO_ENCONTRADA.getTitle())
				.detail(e.getMessage())
				.build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> trataEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(HttpStatus.CONFLICT.value())
				.type(ProblemType.ENTIDADE_EM_USO.getUrl())
				.title(ProblemType.ENTIDADE_EM_USO.getTitle())
				.detail(e.getMessage())
				.build();

		return handleExceptionInternal(e, erro, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value())
				.type(ProblemType.RECURSO_NAO_ENCONTRADO.getUrl())
				.title(ProblemType.RECURSO_NAO_ENCONTRADO.getTitle())
				.detail(String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL()))
				.build();

		return handleExceptionInternal(ex, erro, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value())
				.type(ProblemType.DADO_INVALIDO.getUrl())
				.title(ProblemType.DADO_INVALIDO.getTitle())
				.detail("Identificamos que um ou mais campos foram preenchidos incorretamente. Por favor, "
						+ "revise as informações e preencha os campos corretamente antes de tentar novamente.")
				.build();
		
		return handleExceptionInternal(ex, erro, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		  List<Field> appiErroFields = ex.getBindingResult().getFieldErrors().stream()
		 .map(x -> ApiErro.Field
				 .builder()
				 .nome(x.getField())
				 .userMessage(x.getDefaultMessage())
				 .build())
		 .collect(Collectors.toList());
		  
		ApiErro erro = ApiErro.builder()
				.timestamp(LocalDateTime.now())
				.Status(status.value())
				.title(ProblemType.DADO_INVALIDO.getTitle())
				.type(ProblemType.DADO_INVALIDO.getUrl())
				.detail("Um ou mais campos estão invalido . faça o preenchimento correto e tente novamente")
				.fields(appiErroFields)
				.build();
		
		return handleExceptionInternal(ex , erro ,new HttpHeaders(), status, request);
	}

}
