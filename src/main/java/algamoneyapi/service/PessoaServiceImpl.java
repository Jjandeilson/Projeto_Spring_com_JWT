package algamoneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import algamoneyapi.model.Pessoa;
import algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService{

	@Autowired
	private PessoaRepository pessoaRepository;

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
	
	private Optional<Pessoa> verficarPessoaNoBanco(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		
		if(!pessoa.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return pessoa;
	}
}
