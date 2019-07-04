package br.estagio.ftp.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO  {

    private String password;
    private String nomeUsuario;
    private String cpf;


    public Usuario dtoFromUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(this.getNomeUsuario());
        usuario.setPassword(this.getPassword());
        usuario.setCpf(this.getCpf());

        return usuario;
    }
}