package algamoneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import algamoneyapi.model.Lancamento;
import algamoneyapi.model.Pessoa;
import algamoneyapi.repository.LancamentoRepository;
import algamoneyapi.repository.PessoaRepository;
import algamoneyapi.service.exception.PessoaInexistenteOuInativa;

@Service
public class LancamentoServiceImpl implements LancamentoService{

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}

	@Override
	public Lancamento buscar(Long codigo) {
		Optional<Lancamento> lancamento = verificarLancamentoNoBanco(codigo);
		return lancamento.get();
	}

	@Override
	public Lancamento criar(Lancamento lancamento) {
		pessoaInexistenteOUInativa(lancamento.getPessoa().getCodigo());
		return lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long codigo) {
		Optional<Lancamento> lancamento = verificarLancamentoNoBanco(codigo);
		lancamentoRepository.delete(lancamento.get());		
	}

	@Override
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Optional<Lancamento> lancamentoOp = verificarLancamentoNoBanco(codigo);
		pessoaInexistenteOUInativa(lancamento.getPessoa().getCodigo());
		BeanUtils.copyProperties(lancamento, lancamentoOp.get(), "codigo");
		return lancamentoRepository.save(lancamentoOp.get());
	}
	
	private Optional<Lancamento> verificarLancamentoNoBanco(Long codigo) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
		
		if(!lancamento.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return lancamento;
	}
	
	private void pessoaInexistenteOUInativa(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		
		if(!pessoa.isPresent() || !pessoa.get().isAtivo()) {
			throw new PessoaInexistenteOuInativa();
		}
	}
}
