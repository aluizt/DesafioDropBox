package br.estagio.ftp.service.exception;


public class UsuarioJaCadastradoException extends RuntimeException{

    public UsuarioJaCadastradoException(String message) {
        super(message);
    }
}
