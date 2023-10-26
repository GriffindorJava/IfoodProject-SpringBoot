package com.kaique.ifood.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaique.ifood.exception.EntidadeEmUsoException;

@ControllerAdvice
public class ExcecaoController {

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<String> handleIntegrityConstraintViolation(EntidadeEmUsoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}