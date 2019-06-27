package br.estagio.ftp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioHateoas extends ResourceSupport {

    @Id
    private String idUsuario;
    private String password;
    private String nomeUsuario;
    private String cpf;
    private List<Amigos> amigos;



}
