package algamoneyapi.model;

import javax.persistence.Embeddable;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@Embeddable
public class Endereco {

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String estado;
}
