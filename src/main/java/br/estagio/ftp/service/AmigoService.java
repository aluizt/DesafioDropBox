package br.estagio.ftp.service;


import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.repository.UsuarioRepository;
import br.estagio.ftp.service.exception.ObjetoNaoEncontradoException;
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

        if(optional.isPresent()){
            Usuario usuario= (Usuario) optional.get();

            if(usuario.getAmigos()==null){
                throw new ObjetoNaoEncontradoException("Não existe amigos cadastrados para o usuario "+id);
            }
            return usuario.getAmigos();
        }
        else{

            throw new ObjetoNaoEncontradoException("Usuario "+id+" não cadastrado");
        }


    }



    public List<Amigos> incluirAmigo(String id, Amigos amigo)  {


        Optional optionalUsuario =this.repo.findById(id);
        Optional optionalAmigo   =this.repo.findById(amigo.getIdAmigo());

        if(optionalUsuario.isPresent()) {

            Usuario usuario  = (Usuario) optionalUsuario.get();

            if(optionalAmigo.isPresent()){

                Usuario usuario2 = (Usuario) optionalAmigo.get();

                List<Amigos> listaAmigos = verificaNull(usuario);

                listaAmigos.add(amigo);
                usuario.setAmigos(listaAmigos);

                this.repo.save(usuario);

                Amigos novoAmigo = new Amigos();

                novoAmigo.setIdAmigo(usuario.getIdUsuario());
                novoAmigo.setNomeAmigo(usuario.getNomeUsuario());

                List<Amigos> listaAmigos2 = verificaNull(usuario2);

                listaAmigos2.add(novoAmigo);
                usuario2.setAmigos(listaAmigos2);
                this.repo.save(usuario2);

                return listaAmigos;

            }else{
                throw new ObjetoNaoEncontradoException("Amigo "+id+" não foi localizado");
            }

        }else{
            throw new ObjetoNaoEncontradoException("Usuario "+id+" não foi localizado");
        }

    }

    public List<Amigos> deletar(String idUsuario, String idAmigo){

        List<Amigos> lista = deletarAmigo(idUsuario,idAmigo);
        this.deletarAmigo(idAmigo,idUsuario);

        return lista;

    }

    private List<Amigos> deletarAmigo(String id01, String id02){

        Optional optional=this.repo.findById(id01);
        Usuario usuario = new Usuario();

        if(optional.isPresent()){
            usuario= (Usuario) optional.get();
        }else{
            throw new ObjetoNaoEncontradoException("Usuario não localizado");
        }

        if(usuario.getAmigos()==null){
            throw new ObjetoNaoEncontradoException("Usuario não possui amigas compartilhados");
        }

        List<Amigos> listaAmigos = usuario.getAmigos();

        int tamanho = listaAmigos.size();

        listaAmigos.removeIf(amigo -> amigo.getIdAmigo().equals(id02));

        if(listaAmigos.size()==tamanho){
            throw new ObjetoNaoEncontradoException("Amigo informado não localizado");
        }

        usuario.setAmigos(listaAmigos);

        this.repo.save(usuario);

        return listaAmigos;
    }

    private List<Amigos> verificaNull(Usuario usuario){

        List<Amigos> lista = new ArrayList<>();

        if(usuario.getAmigos()!=null) {
            lista=usuario.getAmigos();
        }
        return lista;
    }

}
