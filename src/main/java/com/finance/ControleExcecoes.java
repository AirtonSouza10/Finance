package com.finance;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.finance.dto.ObjetoErroDTO;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ExceptionFinance.class })
	public ResponseEntity<Object> handleExceptionException(ExceptionFinance exe) {
		var objetoErroDTO = new ObjetoErroDTO();
		objetoErroDTO.setError(exe.getMessage());
		objetoErroDTO.setCode(HttpStatus.OK.toString());
		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.OK);
	}

	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		var objetoErroDTO = new ObjetoErroDTO();
		var msg = "";

		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else {
			msg = ex.getMessage();
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(status.value() + "==> " + status.getReasonPhrase());

		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception exe) {
		var objetoErroDTO = new ObjetoErroDTO();
		var msg = "";

		if (exe instanceof DataIntegrityViolationException) {
			msg = "Integridade no Banco: " + ((DataIntegrityViolationException) exe).getCause().getCause().getMessage();
		} else if (exe instanceof ConstraintViolationException) {
			msg = "Chave Estrangeira: " + ((ConstraintViolationException) exe).getCause().getCause().getMessage();
		} else if (exe instanceof SQLException) {
			msg = "SQL: " + ((SQLException) exe).getCause().getCause().getMessage();
		} else {
			msg = exe.getMessage();
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
