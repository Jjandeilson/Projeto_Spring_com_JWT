package algamoneyapi.service;

import java.util.List;

import algamoneyapi.model.Categoria;

public interface CategoriaService {

	List<Categoria> listar();
	Categoria buscar(Long codigo);
	Categoria criar(Categoria categoria);
	void remover(Long codigo);
	Categoria atualizar(Long codigo, Categoria categoria);
}
