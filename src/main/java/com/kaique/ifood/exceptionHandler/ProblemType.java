package com.kaique.ifood.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado" , "Recurso não encontrado"),
	PARAMETRO_INVALIDO("/parametro-invalido" , "Parâmetro Inválido" ),
	CORPO_ILEGIVEL("/corpo-ilegivel" ,"Corpo ilegível"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada" , "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso" , "Entidade em uso"),
	CHAVE_ESTRANGEIRA_NAO_ENCONTRA("/chave-estrangeira-nao-encontrada" , "Chave estrangeira não encontrada"),
	NEGOCIO( "/negocio", "Negócio");
	
	private String url;
	private String title;
	
	ProblemType(String path, String title) {
		this.url = "https//kaique.com.br" + path;
		this.title = title;
	}	
}
