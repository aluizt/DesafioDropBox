package br.estagio.ftp.control;


import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.model.UsuarioDTO;
import br.estagio.ftp.repository.UsuarioRepository;
import br.estagio.ftp.service.exception.ObjetoNaoEncontradoException;
import br.estagio.ftp.util.UsuariosParaTeste;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestaEndPointsUsuario {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuariosParaTeste usuariosParaTeste;


    @MockBean
    private UsuarioRepository repository;


    @Test
    public void listaUsuariosRetornandoHttpStatus200(){
        //cenario
        List<Usuario> listaUsuarios = usuariosParaTeste.criaUsuariosParaTeste();
        BDDMockito.when(repository.findAll()).thenReturn(listaUsuarios);

        //ação
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/usuarios",String.class);

        HttpStatus estatusEsperado  = HttpStatus.OK;
        HttpStatus estatusRetornado = responseEntity.getStatusCode();

        //verificação

        Assert.assertEquals(estatusRetornado,estatusEsperado);
    }

    @Test
    public void listaUsuariosRetornandoHttpStatus404(){
        //cenario
        BDDMockito.when(repository.findAll()).thenThrow(ObjetoNaoEncontradoException.class);

        //ação
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/usuarios",String.class);

        HttpStatus statusEsperado  = HttpStatus.NOT_FOUND;
        HttpStatus statusRetornado = responseEntity.getStatusCode();

        //verificação
        Assert.assertEquals(statusEsperado,statusRetornado);
    }

    @Test
    public void getUsuarioPorIdRetornandoStatusCode200(){
        //cenario
        Usuario usuario = usuariosParaTeste.criaUsuarioParaTeste();
        BDDMockito.when(repository.findById(usuario.getIdUsuario())).thenReturn(java.util.Optional.of(usuario));

        //ação
        ResponseEntity<String> responseEntity =
                testRestTemplate.getForEntity("/usuarios/{id}",String.class,usuario.getIdUsuario());

        HttpStatus estatusEsperado  = HttpStatus.OK;
        HttpStatus estatusRetornado = responseEntity.getStatusCode();

        //verificação
        Assert.assertEquals(estatusRetornado,estatusEsperado);

    }

    @Test
    public void getUsuarioPorIdRetornandoStatusCode404(){
        //cenario
        Usuario usuario = usuariosParaTeste.criaUsuarioParaTeste();
        BDDMockito.when(repository.findById(any())).thenThrow(ObjetoNaoEncontradoException.class);

        //ação
        ResponseEntity<String> responseEntity =
                testRestTemplate.getForEntity("/usuarios/{id}",String.class,usuario.getIdUsuario());

        HttpStatus statusEsperado  = HttpStatus.NOT_FOUND;
        HttpStatus statusRetornado = responseEntity.getStatusCode();

        //verificação

        Assert.assertEquals(statusRetornado,statusEsperado);

    }

    @Test
    public void postUsuarioRetornandoStatusCode201(){
        //cenario
        Usuario usuario=usuariosParaTeste.criaUsuarioParaTeste();
        UsuarioDTO usuarioDTO=usuariosParaTeste.criaUsuarioDTOParaTeste();
        BDDMockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        //ação
        ResponseEntity<Usuario> responseEntity =
                testRestTemplate.postForEntity("/usuarios",usuarioDTO,Usuario.class);

        HttpStatus estatusEsperado  = HttpStatus.CREATED;
        HttpStatus estatusRetornado = responseEntity.getStatusCode();

        //verificação
        Assert.assertEquals(estatusRetornado,estatusEsperado);
    }

    @Test
    public void postUsuarioRetornandoStatusCode404(){
        //cenario
        Usuario usuario=usuariosParaTeste.criaUsuarioParaTeste();
        UsuarioDTO usuarioDTO=usuariosParaTeste.criaUsuarioDTOParaTeste();
        BDDMockito.when(repository.save(any(Usuario.class))).thenThrow(ObjetoNaoEncontradoException.class);

        //ação
        ResponseEntity<Usuario> responseEntity =
                testRestTemplate.postForEntity("/usuarios",usuarioDTO,Usuario.class);

        HttpStatus statusEsperado  = HttpStatus.NOT_FOUND;
        HttpStatus statusRetornado = responseEntity.getStatusCode();

        //verificação

        Assert.assertEquals(statusRetornado,statusEsperado);

    }
}















