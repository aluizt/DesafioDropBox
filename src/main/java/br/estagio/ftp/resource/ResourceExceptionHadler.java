package br.estagio.ftp.resource;


import br.estagio.ftp.service.exception.ObjetoNaoEncontradoException;
import br.estagio.ftp.service.exception.StandardError;
import br.estagio.ftp.service.exception.UsuarioJaCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHadler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjetoNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "não encontroado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }




    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<StandardError> existingUserException(UsuarioJaCadastradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "usuario já cadastrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
