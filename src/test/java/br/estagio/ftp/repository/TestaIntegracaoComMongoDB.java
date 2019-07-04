package br.estagio.ftp.repository;


import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.model.UsuarioDTO;
import br.estagio.ftp.util.UsuariosParaTeste;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestaIntegracaoComMongoDB {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuariosParaTeste usuariosParaTeste;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioDTO;

    @Before
    public void start(){
        usuarioDTO=usuariosParaTeste.criaUsuarioDTOParaTeste();
    }


    @Test
    public void testaInclusaoDeNovoUsuarioNoMongoDB(){

        Usuario usuario=this.usuarioRepository.save(usuarioDTO.dtoFromUsuario());

        Assert.assertEquals(usuario.getNomeUsuario(),usuarioDTO.getNomeUsuario());

        removeUsuario(usuario.getIdUsuario());

    }

    @Test
    public void testaInclusaoDeNovoAmigoNoMongoDB(){

        //cenario

        Usuario usuario=this.usuarioRepository.save(usuarioDTO.dtoFromUsuario());
        Amigos amigos = new Amigos();
        amigos.setIdAmigo("12345678910");
        amigos.setNomeAmigo("xxxxxxxxxx");
        List<Amigos> listaAmigos = new ArrayList<>();
        listaAmigos.add(amigos);
        usuario.setAmigos(listaAmigos);

        //ação

        Usuario usuarioNovo=this.usuarioRepository.save(usuario);
        List<Amigos> lista2 =usuarioNovo.getAmigos();

        //verificação

        Assert.assertEquals(usuario.getAmigos().get(0).getIdAmigo(),usuarioNovo.getAmigos().get(0).getIdAmigo());
        removeUsuario(usuario.getIdUsuario());
    }

    private void removeUsuario(String id){
        this.usuarioRepository.deleteById(id);
    }


}
