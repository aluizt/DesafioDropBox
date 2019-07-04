package br.estagio.ftp.model;


import br.estagio.ftp.control.AmigosControl;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AmigosHateoasAssembler extends ResourceAssemblerSupport<Amigos, AmigosHateoas> {

    public AmigosHateoasAssembler() {

        super(AmigosControl.class, AmigosHateoas.class);
    }

    @Override
    public AmigosHateoas toResource(Amigos amigo) {
        if(amigo==null){
            return null;
        }
        AmigosHateoas amigosHateoas = createResourceWithId(amigo.getIdAmigo(), amigo);
        amigosHateoas.setIdAmigo(amigo.getIdAmigo());
        amigosHateoas.setNomeAmigo(amigo.getNomeAmigo());

        addLinks(amigosHateoas);
        return  amigosHateoas;
    }

    public List<AmigosHateoas> toResourceList(List<Amigos> listaAmigos) {
        if(listaAmigos==null){
            return null;
        }
        List<AmigosHateoas> listaHateoas = new ArrayList<>();
         AmigosHateoas amigosHateoas = new AmigosHateoas();

        for(Amigos u:listaAmigos) {
            amigosHateoas = createResourceWithId(u.getIdAmigo(), u);
            amigosHateoas.setIdAmigo(u.getIdAmigo());
            amigosHateoas.setNomeAmigo(u.getNomeAmigo());
            addLinks(amigosHateoas);
            listaHateoas.add(amigosHateoas);
        }
        return  listaHateoas;
    }

    private void addLinks(AmigosHateoas amigosHateoas) {
        //Links de exemplo você pode usar o linkTo(methodOn())
        amigosHateoas.add(new Link("http://localhost:8080/", "alterar"));
        amigosHateoas.add(new Link("http://localhost:8080/", "deletar"));

    }


}
