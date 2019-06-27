package br.estagio.ftp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class AmigosHateoas extends ResourceSupport {

    private String idAmigo;
    private String nomeAmigo;
}
