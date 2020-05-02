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
import algamoneyapi.model.Pessoa;
import algamoneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<Pessoa> listar() {
		return pessoaService.listar();
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> buscar(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaService.buscar(codigo);
		return ResponseEntity.ok(pessoa);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRO_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> criar(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaS = pessoaService.criar(pessoa);
		publisher.publishEvent(new AplicacaoEvent(this, pessoaS.getCodigo(), response));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaS);
	}
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Void> remover(@PathVariable Long codigo) {
		pessoaService.remover(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @RequestBody @Valid Pessoa pessoa) {
		Pessoa pessoaA = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaA);
	}
	
	@PutMapping("/{codigo}/ativo")
	public ResponseEntity<Void> atualizarPrropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){
		pessoaService.atualiazarPropriedadeAtivo(codigo, ativo);
		return ResponseEntity.noContent().build();
	}
	
}
