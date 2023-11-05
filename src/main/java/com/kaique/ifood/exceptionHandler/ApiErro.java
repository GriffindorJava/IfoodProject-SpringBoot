package com.kaique.ifood.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiErro {
	
	private Integer Status;
	private String type;
	private String title;
	private String detail;
}
