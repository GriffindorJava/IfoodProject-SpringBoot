package com.kaique.ifood.core.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { MultiploValidator.class })
public @interface Multiplo {

	/*
	 * A anotação @Multiplo foi criada unicamente para fins didáticos e não está atualmente em uso no código. 
	 * Sua criação teve como objetivo proporcionar uma compreensão conceitual e ilustrativa, mas, na prática,
	 * não está sendo empregada no sistema."
	 */

	// Uma anotação do Bean validation precisa ter essas 3 propriedades
	String message() default "Múltiplo inválido";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// Propriedades personalizadas
	int numero();

}