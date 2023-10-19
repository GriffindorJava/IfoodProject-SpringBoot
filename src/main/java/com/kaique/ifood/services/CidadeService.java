package com.kaique.ifood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.kaique.ifood.entities.Cidade;
import com.kaique.ifood.exception.EntidadeEmUsoException;
import com.kaique.ifood.exception.EntidadeNaoEncontradaException;
import com.kaique.ifood.repositories.CozinhaRepository;
import com.kaique.ifood.repositories.CidadeRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public List<Cidade> listar() {
		return repository.findAll();
	}

	public Optional<Cidade> buscaPorId(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Cidade adiciona(Cidade cidade) throws ConstraintViolationException {

		if (cidade.getEstado() != null) {//implementação temporaria ale eu começar a usar validacões 
			if (cidadeRepository.findById(cidade.getEstado().getId()).isEmpty()) {
				throw new EntidadeNaoEncontradaException(
						String.format("Código %d de cozinha não foi encontrado ", cidade.getEstado().getId()));
			}
		}
		return repository.save(cidade);
	}

	@Transactional
	public Cidade atualiza(Long id, Cidade NovoCidade) throws ConstraintViolationException {
		Cidade cidadeAtual = repository.findById(id).get();
		BeanUtils.copyProperties(NovoCidade, cidadeAtual, "id");
		return repository.save(cidadeAtual);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			if (repository.findById(id).isEmpty())
				throw new EntidadeNaoEncontradaException(String.format("Código %d não encontrado ", id));
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removido , pois está em uso ", id));
		}

	}
}
