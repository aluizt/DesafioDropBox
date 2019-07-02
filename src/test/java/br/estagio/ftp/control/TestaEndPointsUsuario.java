package br.estagio.ftp.control;


import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.model.UsuarioDTO;
import br.estagio.ftp.repository.UsuarioRepository;
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
    public void getUsuarioPorIdRetornandoStatusCode200(){
        //cenario
        Usuario usuario = usuariosParaTeste.criaUsuarioParaTeste();        //ação
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
    public void postUsuarioRetornandoStatusCode200(){
        //cenario
        Usuario usuario=usuariosParaTeste.criaUsuarioParaTeste();
        UsuarioDTO usuarioDTO=usuariosParaTeste.criaUsuarioDTOParaTeste();
        BDDMockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        //ação
        ResponseEntity<Usuario> responseEntity =
                testRestTemplate.postForEntity("/usuarios",usuarioDTO,Usuario.class);

        //verificação
        Assert.assertEquals(responseEntity.getBody().getNomeUsuario(),usuario.getNomeUsuario());
    }
}















