package br.estagio.ftp.service;

import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.repository.UsuarioRepository;
import br.estagio.ftp.util.UsuariosParaTeste;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Component
public class TestaServiceUsuarios {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuariosParaTeste  usuariosParaTeste;

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository repository;

    private List<Usuario> listaUsuarios;


    @Test
    public void getListarTodosUsuarios(){
        List<Usuario> listaUsuarios = usuariosParaTeste.criaUsuariosParaTeste();
        BDDMockito.when(repository.findAll()).thenReturn(listaUsuarios);

        List<Usuario> lista = usuarioService.listarTodos();

        Assert.assertEquals(lista.size(),2);

    }
    @Test
    public void getUsuarioPorIdComparandoOIdRetornado(){
        //cenario
        Usuario usuario = usuariosParaTeste.criaUsuarioParaTeste();
        BDDMockito.when(repository.findById(usuario.getIdUsuario())).thenReturn(java.util.Optional.of(usuario));

        //ação
        Usuario usuarioRetorno = usuarioService.consultarId(usuario.getIdUsuario());

        //verificação

        Assert.assertEquals(usuarioRetorno.getIdUsuario(),usuario.getIdUsuario());
    }

    @Test
    public void postIncluindoUmNovoUsuario(){
        Usuario usuario=usuariosParaTeste.criaUsuarioParaTeste();
        BDDMockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioRetorno = usuarioService.incluirUsuario(usuario);

        Assert.assertEquals(usuarioRetorno.getNomeUsuario(),usuario.getNomeUsuario());
    }

}
