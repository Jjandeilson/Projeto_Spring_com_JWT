package algamoneyapi.service;

import java.util.List;

import algamoneyapi.model.Lancamento;

public interface LancamentoService {

	List<Lancamento> listar();
	Lancamento buscar(Long codigo);
	Lancamento criar(Lancamento lancamento);
	void remover(Long codigo);
	Lancamento atualizar(Long codigo, Lancamento lancamento);
}
