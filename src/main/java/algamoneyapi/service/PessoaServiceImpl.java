package algamoneyapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import algamoneyapi.model.Pessoa;
import algamoneyapi.model.Pessoa_;
import algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService{

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@Override
	public Pessoa buscar(Long codigo) {
		Optional<Pessoa> pessoa = verficarPessoaNoBanco(codigo);
		return pessoa.get();
	}

	@Override
	public Pessoa criar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	@Override
	public void remover(Long codigo) {
		Optional<Pessoa> pessoa = verficarPessoaNoBanco(codigo);
		pessoaRepository.delete(pessoa.get());		
	}

	@Override
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Optional<Pessoa> pessoaOp = verficarPessoaNoBanco(codigo);
		BeanUtils.copyProperties(pessoa, pessoaOp.get(), "codigo");
		return pessoaRepository.save(pessoaOp.get());
	}
	
	@Override
	public void atualiazarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Optional<Pessoa> pessoa = verficarPessoaNoBanco(codigo);
		pessoa.get().setAtivo(ativo);
		pessoaRepository.save(pessoa.get());
	}
	
	@Override
	public Page<Pessoa> pesquisar(String nome, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(nome, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		adicionarRestricoesDaPagina(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(nome));
	}
	
	private Long total(String nome) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(nome, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDaPagina(TypedQuery<Pessoa> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalResgistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalResgistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalResgistrosPorPagina);
	}

	private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(nome)) {
			predicates.add(builder.like(builder.lower(root.get(Pessoa_.NOME)), "%" + nome.toLowerCase() + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Optional<Pessoa> verficarPessoaNoBanco(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		
		if(!pessoa.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return pessoa;
	}

}
