package br.estagio.ftp.control;


import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.UsuarioHateoas;
import br.estagio.ftp.service.AmigoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@ExposesResourceFor(UsuarioHateoas.class)
@RequestMapping(value = "/amigos")
public class AmigosControl {

    @Autowired
    private AmigoService service;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Retorna todos os amigos do usuario ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, todos os amigos foram listados"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi encontrado os amigos deste usuario")
    }
    )
    public ResponseEntity<List<Amigos>> listarTodosAmigosDoUsuario(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable("id") String id){

        List<Amigos> lista = this.service.listarTodosAmigos(id);

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @PostMapping(value = "/{id}")
    @ApiOperation(value = "Incluir um amigo na lista de amigos do usuario ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, amigo incluido na lista"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi possivel incluir este amigo na lista do usuario")
    }
    )
    public ResponseEntity<List<Amigos>> incluirAmigo(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable("id") String id,
            @ApiParam(value = "Identificador do amigo do usuário")
            @RequestParam(value = "idAmigo")String idAmigo){

        List<Amigos> lista = this.service.incluirAmigo(id,idAmigo);

        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{idUsuario}/{idAmigo}")
    @ApiOperation(value = "Remove um amigo da lista de amigos do usuario ... " )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso, amigo removido da lista"),
            @ApiResponse(code = 401, message = "Você não esta autorizado"),
            @ApiResponse(code = 403, message = "Você não tem acesso a este recurso"),
            @ApiResponse(code = 404, message = "Não foi possivel remover este amigo da lista do usuario")
    }
    )
    public ResponseEntity<List<Amigos>> deletarAmigo(
            @ApiParam(value = "Identificador do usuário")
            @PathVariable("idUsuario") String idUsuario,
            @ApiParam(value = "Identificador do amigo do usuário")
            @PathVariable("idAmigo") String idAmigo){

        List<Amigos> lista = this.service.deletar(idUsuario,idAmigo);

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


}
