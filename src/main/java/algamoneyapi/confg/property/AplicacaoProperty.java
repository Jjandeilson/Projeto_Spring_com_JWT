package algamoneyapi.confg.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties("aplicacao")
public class AplicacaoProperty {
	
	private String originPermitida = "http://localhost:4200";
	
	private final Seguranca seguranca = new Seguranca();

	@Getter
	@Setter
	public static class Seguranca {
		private boolean enableHttps;
	}
}
