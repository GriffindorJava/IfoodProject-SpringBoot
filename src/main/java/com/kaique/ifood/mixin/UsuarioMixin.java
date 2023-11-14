package com.kaique.ifood.mixin;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioMixin {

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private OffsetDateTime dataCadastro;
}
