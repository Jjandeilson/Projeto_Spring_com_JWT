package algamoneyapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import algamoneyapi.model.Lancamento;
import algamoneyapi.repository.filter.LancamentoFilter;
import algamoneyapi.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	// public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
