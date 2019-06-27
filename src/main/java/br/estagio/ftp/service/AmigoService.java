package br.estagio.ftp.service;


import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AmigoService {

    @Autowired
    private UsuarioRepository repo;

    public List<Amigos> listarTodosAmigos(String id){
        Optional optional=this.repo.findById(id);

        if(optional.isEmpty()){
            return null;
        }
        Usuario usuario= (Usuario) optional.get();

        List<Amigos> amigos = usuario.getAmigos();
        return amigos;
    }

    public List<Amigos> incluirAmigo(String id, Amigos amigo){

        Optional optionalUsuario =this.repo.findById(id);
        Optional optionalAmigo   =this.repo.findById(amigo.getIdAmigo());

        if(optionalUsuario.isEmpty() || optionalAmigo.isEmpty()) {
            return null;
        }

        Usuario usuario  = (Usuario) optionalUsuario.get();
        Usuario usuario2 = (Usuario) optionalAmigo.get();

        List<Amigos> listaAmigos = new ArrayList<>();

        if(usuario.getAmigos()!=null) {
           listaAmigos=usuario.getAmigos();
        }

        listaAmigos.add(amigo);
        usuario.setAmigos(listaAmigos);
        this.repo.save(usuario);

        Amigos novoAmigo = new Amigos();

        novoAmigo.setIdAmigo(usuario.getIdUsuario());
        novoAmigo.setNomeAmigo(usuario.getNomeUsuario());

        List<Amigos> listaAmigos2 = new ArrayList<>();

        if(usuario2.getAmigos()!=null){
            listaAmigos2=usuario2.getAmigos();
        }

        listaAmigos2.add(novoAmigo);
        usuario2.setAmigos(listaAmigos2);
        this.repo.save(usuario2);

        return listaAmigos;
    }

    public List<Amigos> deletarAmigo(String idUsuario, String idAmigo){
        Optional optional=this.repo.findById(idUsuario);
        if(optional.isEmpty()){
            return null;
        }
        Usuario usuario= (Usuario) optional.get();
        if(usuario.getAmigos()==null){
            return null;
        }

        List<Amigos> listaAmigos = usuario.getAmigos();
        int tamanho = listaAmigos.size();

        listaAmigos.removeIf(amigo -> amigo.getIdAmigo().equals(idAmigo));

        if(listaAmigos.size()==tamanho){
            return null;
        }
        usuario.setAmigos(listaAmigos);
        listaAmigos.forEach(amigos -> System.out.println(amigos.getIdAmigo()+" "+amigos.getNomeAmigo()) );
        this.repo.save(usuario);

        return listaAmigos;
    }

}
