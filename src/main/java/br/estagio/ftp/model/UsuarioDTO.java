package br.estagio.ftp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class UsuarioDTO  {

    private String password;
    private String nomeUsuario;
    private String cpf;
    private List<Amigos> amigos;
}