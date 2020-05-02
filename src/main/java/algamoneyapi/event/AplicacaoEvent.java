package algamoneyapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AplicacaoEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	private Long codigo;
	private HttpServletResponse response;

	public AplicacaoEvent(Object source, Long codigo, HttpServletResponse response) {
		super(source);
		this.codigo = codigo;
		this.response = response;
	}

}
