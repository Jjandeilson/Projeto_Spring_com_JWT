package algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import algamoneyapi.event.AplicacaoEvent;
import algamoneyapi.model.Categoria;
import algamoneyapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> listar() {
		return categoriaService.listar();
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscar(@PathVariable Long codigo) {
		Categoria  categoria = categoriaService.buscar(codigo);
		return ResponseEntity.ok(categoria);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRO_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@RequestBody @Valid Categoria categoria, HttpServletResponse response) {
		Categoria categoriaS = categoriaService.criar(categoria);
		publisher.publishEvent(new AplicacaoEvent(this, categoriaS.getCodigo(), response));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaS);
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> remover(@PathVariable Long codigo) {
		categoriaService.remover(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo, @RequestBody @Valid Categoria categoria) {
		Categoria categoriaA = categoriaService.atualizar(codigo, categoria);
		return ResponseEntity.ok(categoriaA);
	}
	
}
