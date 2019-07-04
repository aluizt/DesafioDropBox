package br.estagio.ftp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UsuarioHateoas that = (UsuarioHateoas) o;
        return Objects.equals(idUsuario, that.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idUsuario);
    }
}
