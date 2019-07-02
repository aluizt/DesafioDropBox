package br.estagio.ftp.util;

import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.model.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuariosParaTeste {


    public Usuario criaUsuarioParaTeste(){

        Usuario u1 = new Usuario();
        u1.setIdUsuario("1001");
        u1.setNomeUsuario("Alexandre");
        return u1;
    }


    public UsuarioDTO criaUsuarioDTOParaTeste(){

        UsuarioDTO u1 = new UsuarioDTO();
        u1.setNomeUsuario("Alexandre");
        return u1;
    }

    public List<Usuario> criaUsuariosParaTeste(){

        List<Usuario> lista = new ArrayList<>();

        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();


        u1.setIdUsuario("1001");
        u1.setNomeUsuario("Alexandre");

        u2.setIdUsuario("2002");
        u2.setNomeUsuario("Janice");

        lista.add(u1);
        lista.add(u2);

        return lista;

    }
}
