package com.kaique.ifood.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada" , "Entidade não encontrada");

	private String url;
	private String title;
	
	ProblemType(String path, String title) {
		this.url = "https//kaique.com.br" + path;
		this.title = title;
	}	
}
