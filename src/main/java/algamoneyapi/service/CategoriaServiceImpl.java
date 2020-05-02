package algamoneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import algamoneyapi.model.Categoria;
import algamoneyapi.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria buscar(Long codigo) {
		Optional<Categoria> categoria = verificarCategoriaNoBanco(codigo);
		return categoria.get();
	}

	@Override
	public Categoria criar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	public void remover(Long codigo) {
		Optional<Categoria> categoria = verificarCategoriaNoBanco(codigo);
		categoriaRepository.delete(categoria.get());		
	}

	@Override
	public Categoria atualizar(Long codigo, Categoria categoria) {
		Optional<Categoria> categoriaOp = verificarCategoriaNoBanco(codigo);
		BeanUtils.copyProperties(categoria, categoriaOp.get(), "codigo");
		return categoriaRepository.save(categoriaOp.get());
	}
	
	private Optional<Categoria> verificarCategoriaNoBanco(Long codigo) {
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);
		
		if(!categoria.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categoria;
	}
}
