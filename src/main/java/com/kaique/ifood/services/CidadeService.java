package com.kaique.ifood.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaique.ifood.entities.Cidade;
import com.kaique.ifood.exception.CidadeNaoEncontradaException;
import com.kaique.ifood.exception.NegocioException;
import com.kaique.ifood.repositories.CidadeRepository;
import com.kaique.ifood.repositories.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private EstadoRepository estado;

	public List<Cidade> listar() {
		return repository.findAll();
	}

	public Cidade buscaPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

	@Transactional
	public Cidade adiciona(Cidade cidade) {
		estado.findById(cidade.getEstado().getId()).orElseThrow(() -> new NegocioException(
				String.format("Estado de código %d não encontrado", cidade.getEstado().getId())));

		return repository.save(cidade);
	}

	@Transactional
	public Cidade atualiza(Long id, Cidade NovaCidade) {

		if (NovaCidade.getEstado().getId() != null)
			estado.findById(NovaCidade.getEstado().getId()).orElseThrow(() -> new NegocioException(
					String.format("Estado de código %d não encontrado", NovaCidade.getEstado().getId())));

		Cidade cidadeAtual = buscaPorId(id);
		BeanUtils.copyProperties(NovaCidade, cidadeAtual, "id");
		return repository.save(cidadeAtual);
	}

	@Transactional
	public void deletar(Long id) {
		buscaPorId(id);
		repository.deleteById(id);

	}
}
