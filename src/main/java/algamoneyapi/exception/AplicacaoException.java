package algamoneyapi.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import algamoneyapi.service.exception.PessoaInexistenteOuInativa;
import lombok.Getter;

@ControllerAdvice
public class AplicacaoException extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource source;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsu = source.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.getCause().toString();
		return handleExceptionInternal(ex, new Erro(mensagemUsu, mensagemDev), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = validarCampos(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemUsu = source.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.toString();
		return handleExceptionInternal(ex, new Erro(mensagemUsu, mensagemDev), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		String mensagemUsu = source.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDev = ExceptionUtils.getRootCauseMessage(ex);
		return handleExceptionInternal(ex, new Erro(mensagemUsu, mensagemDev), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativa.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativa(PessoaInexistenteOuInativa ex, WebRequest request) {
		String mensagemUsu = source.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDev = ex.toString();
		return handleExceptionInternal(ex, new Erro(mensagemUsu, mensagemDev), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> validarCampos(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<Erro>();
		for(FieldError field : bindingResult.getFieldErrors()) {
			String mensagemUsu = source.getMessage(field, LocaleContextHolder.getLocale());
			String mensagemDev = field.toString();
			erros.add(new Erro(mensagemUsu, mensagemDev));
		}
		return erros;
	}

	@Getter
	public class Erro {
		
		private String mensagemUsu;
		private String mensagemDev;
		
		public Erro(String mensagemUsu, String mensagemDev) {
			this.mensagemUsu = mensagemUsu;
			this.mensagemDev = mensagemDev;
		}

	}
}
