package algamoneyapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import algamoneyapi.event.AplicacaoEvent;

@Component
public class AplicacaoListener implements ApplicationListener<AplicacaoEvent>{

	@Override
	public void onApplicationEvent(AplicacaoEvent event) {
		Long codigo = event.getCodigo();
		HttpServletResponse response = event.getResponse();
		
		
		adicionarHeaderLocation(codigo, response);
	}

	private void adicionarHeaderLocation(Long codigo, HttpServletResponse response) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
