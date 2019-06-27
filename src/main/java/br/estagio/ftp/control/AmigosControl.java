package br.estagio.ftp.control;


import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.UsuarioHateoas;
import br.estagio.ftp.service.AmigoService;
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
    public ResponseEntity<List<Amigos>> listarTodosAmigosDoUsuario(@PathVariable("id") String id){

        List<Amigos> lista = this.service.listarTodosAmigos(id);
        if(lista==null){
            return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @PostMapping(value = "/{id}")
    public ResponseEntity<List<Amigos>> incluirAmigo(@PathVariable("id") String id, @RequestBody Amigos amigo){
        List<Amigos> lista = this.service.incluirAmigo(id,amigo);
        if(lista==null){
            return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{idUsuario}/{idAmigo}")
    public ResponseEntity<List<Amigos>> deletarAmigo(@PathVariable("idUsuario") String idUsuario,
                                                     @PathVariable("idAmigo") String idAmigo){

        List<Amigos> lista = this.service.deletarAmigo(idUsuario,idAmigo);
        if(lista==null){
            return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


}
