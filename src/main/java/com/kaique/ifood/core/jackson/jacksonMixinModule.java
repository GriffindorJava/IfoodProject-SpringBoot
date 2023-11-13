package com.kaique.ifood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kaique.ifood.entities.Restaurante;
import com.kaique.ifood.mixin.RestauranteMixin;

@Component
public class jacksonMixinModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	public jacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
	}
}
