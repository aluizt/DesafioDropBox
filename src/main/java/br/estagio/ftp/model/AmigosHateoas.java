package br.estagio.ftp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class AmigosHateoas extends ResourceSupport {

    private String idAmigo;
    private String nomeAmigo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AmigosHateoas that = (AmigosHateoas) o;
        return Objects.equals(idAmigo, that.idAmigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idAmigo);
    }
}
