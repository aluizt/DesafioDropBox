package br.estagio.ftp.model;


import br.estagio.ftp.control.UsuarioControl;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioHateoasAssembler extends ResourceAssemblerSupport<Usuario, UsuarioHateoas> {

    public UsuarioHateoasAssembler() {
        super(UsuarioControl.class, UsuarioHateoas.class);
    }

    @Override
    public UsuarioHateoas toResource(Usuario usuario) {

        if(usuario==null){
            return null;
        }

        UsuarioHateoas usuarioHateoas = createResourceWithId(usuario.getIdUsuario(), usuario);

        usuarioHateoas.setIdUsuario(usuario.getIdUsuario());
        usuarioHateoas.setNomeUsuario(usuario.getNomeUsuario());
        usuarioHateoas.setCpf(usuario.getCpf());
        usuarioHateoas.setPassword(usuario.getPassword());

        List<Amigos> amigos = new ArrayList<Amigos>();

        if(usuario.getAmigos()!=null) {
            for (Amigos a : usuario.getAmigos()) {
                amigos.add(a);
            }
        }
        usuarioHateoas.setAmigos(amigos);

        addLinks(usuarioHateoas);
        return  usuarioHateoas;
    }

    public List<UsuarioHateoas> toResourceList(List<Usuario> listaUsuario) {
        if(listaUsuario==null){
            return null;
        }
        List<UsuarioHateoas> listaHateoas = new ArrayList<>();
        UsuarioHateoas usuarioHateoas = new UsuarioHateoas();

        for(Usuario u:listaUsuario) {
            usuarioHateoas = createResourceWithId(u.getIdUsuario(), u);
            usuarioHateoas.setIdUsuario(u.getIdUsuario());
            usuarioHateoas.setNomeUsuario(u.getNomeUsuario());
            usuarioHateoas.setCpf(u.getCpf());
            usuarioHateoas.setPassword(u.getPassword());


            if(u.getAmigos()!=null) {
                List<Amigos> amigos = new ArrayList<Amigos>();
                for (Amigos a : u.getAmigos()) {
                    amigos.add(a);
                }
                usuarioHateoas.setAmigos(amigos);
            }

            addLinks(usuarioHateoas);
            listaHateoas.add(usuarioHateoas);
        }
        return  listaHateoas;
    }
    public Usuario toDomain(UsuarioHateoas usuarioHateoas) {
        return Usuario.builder()
                .idUsuario(usuarioHateoas.getIdUsuario())
                .nomeUsuario(usuarioHateoas.getNomeUsuario())
                .cpf(usuarioHateoas.getCpf())
                .password(usuarioHateoas.getPassword())
                .build();
    }

    private void addLinks(UsuarioHateoas usuarioHateoas) {
        //Links de exemplo você pode usar o linkTo(methodOn())
        usuarioHateoas.add(new Link("http://localhost:8080/", "alterar"));
        usuarioHateoas.add(new Link("http://localhost:8080/", "deletar"));
    }
}
