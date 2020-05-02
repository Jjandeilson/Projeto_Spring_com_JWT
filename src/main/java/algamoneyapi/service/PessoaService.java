package algamoneyapi.service;

import java.util.List;

import algamoneyapi.model.Pessoa;

public interface PessoaService {

	List<Pessoa> listar();
	Pessoa buscar(Long codigo);
	Pessoa criar(Pessoa pessoa);
	void remover(Long codigo);
	Pessoa atualizar(Long codigo, Pessoa pessoa);
	void atualiazarPropriedadeAtivo(Long codigo, Boolean ativo);
}
