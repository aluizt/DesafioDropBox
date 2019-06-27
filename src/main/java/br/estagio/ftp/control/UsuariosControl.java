package br.estagio.ftp.control;


import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.model.UsuarioHateoas;
import br.estagio.ftp.model.UsuarioHateoasAssembler;
import br.estagio.ftp.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@ExposesResourceFor(UsuarioHateoas.class)
@RequestMapping(value = "/usuarios")
public class UsuariosControl {

    @Autowired
    private UsuarioService service;


    @GetMapping
    @ApiOperation(value = "Retorna todos os usuarios cadastrados ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, todos os usuarios foram listados"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado os usuarios")
    }
    )
    public ResponseEntity<List<UsuarioHateoas>> listarTodosUsuarios(){
        UsuarioHateoasAssembler usuarioHateoasAssembler = new UsuarioHateoasAssembler();

       List<UsuarioHateoas> listaHateoas = usuarioHateoasAssembler
               .toResourceList(this.service.listarTodos());

        return ResponseEntity.ok(listaHateoas);
    }

    @GetMapping(value = "/{page}/{count}")
    @ApiOperation(value = "Retorna todos os usuarios cadastrados, utilizando paginacao em ordem por nome ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, todos os usuarios foram listados, com paginação e ordenados por nome"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado os usuarios")
    }
    )
    public ResponseEntity<Page<Usuario>> listarTodosUsuariosComPaginacao(
                                         @PathVariable int page,
                                         @PathVariable int count){

        return ResponseEntity.ok(this.service.listarTodosPaginado(count,page));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Retorna o usuario atraves do id ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, o usuario foi encontrado"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado este usuario")
    }
    )
    public ResponseEntity<UsuarioHateoas> consultarUsuarioId(@PathVariable("id") String id) {

            UsuarioHateoasAssembler usuarioHateoasAssembler = new UsuarioHateoasAssembler();
            UsuarioHateoas usuarioHateoas=usuarioHateoasAssembler.toResource(this.service.consultarId(id));

            return new ResponseEntity<>(usuarioHateoas, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Inclui um novo usuario ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created, registrado novo usuario com sucesso"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
    }
    )
    public ResponseEntity<UsuarioHateoas> incluir(@RequestBody Usuario usuario){

        UsuarioHateoasAssembler usuarioHateoasAssembler = new UsuarioHateoasAssembler();
        UsuarioHateoas usuarioHateoas =usuarioHateoasAssembler.toResource(this.service.incluirUsuario(usuario));
        return new ResponseEntity<>(usuarioHateoas, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Efetua alteração em um usuario ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, dados alterados "),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado este usuario")
    }
    )
    public ResponseEntity<UsuarioHateoas> alterarUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario){

        usuario.setIdUsuario(id);
        UsuarioHateoasAssembler usuarioHateoasAssembler = new UsuarioHateoasAssembler();
        UsuarioHateoas usuarioHateoas =usuarioHateoasAssembler.toResource(this.service.alterarUsuario(usuario));

        return new ResponseEntity<>(usuarioHateoas, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Remove um ususario ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, o usuario foi excluido"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado o usuario")
    }
    )
    public ResponseEntity deletarUsuario(@PathVariable("id") String id){

        this.service.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletartodos")
    @ApiOperation(value = "Remove todos os usuarios ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, todos os usuarios foram excluidos"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado nenhum usuario")
    }
    )
    public ResponseEntity deletarTodosUsuario(){

        this.service.deletarTodos();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

























