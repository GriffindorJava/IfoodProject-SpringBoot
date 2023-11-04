package com.kaique.ifood.exceptionHandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErro {
	
	private LocalDateTime dataHora;
	private String mensagem;
}
