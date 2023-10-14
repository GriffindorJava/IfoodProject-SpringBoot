package com.kaique.ifood.controlles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaique.ifood.entities.Estado;
import com.kaique.ifood.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService service;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());
	}
}
