package com.kaique.ifood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

	public LocalValidatorFactoryBean validator(MessageSource MassageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(MassageSource);
		return bean;
	}
}
