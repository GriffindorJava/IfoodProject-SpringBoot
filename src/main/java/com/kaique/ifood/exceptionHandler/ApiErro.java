package com.kaique.ifood.exceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiErro {
	
	private LocalDateTime timestamp;
	private Integer Status;
	private String type;
	private String title;
	private String detail;
	private List<Field> fields = new ArrayList<>();
	
	@Getter
	@Builder
	public static class Field {
		private String nome;
		private String userMessage;
	}
}
