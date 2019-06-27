package br.estagio.ftp.service;

import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.repository.UsuarioRepository;
import br.estagio.ftp.service.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;


    public List<Usuario> listarTodos() {
        List<Usuario> lista = repo.findAll();
        if(lista.isEmpty()){
            throw new ObjetoNaoEncontradoException("O arquivo não possui usuarios ");
        }
        return lista;
    }


    public Page<Usuario> listarTodosPaginado(int count, int page) {

        Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nomeUsuario");

        return repo.findAll(pages);
    }


    public Usuario consultarId(String id)  {

        Optional<Usuario> optional = this.repo.findById(id);

        return optional.orElseThrow(() ->
                new ObjetoNaoEncontradoException("O usuario: "+id+" não foi localizado"));
    }


    public Usuario incluirUsuario(Usuario usuario) {
        return this.repo.save(usuario);
    }

    public Usuario alterarUsuario(Usuario u)  {

        this.consultarId(u.getIdUsuario());

        return this.repo.save(u);

    }

    public void deletarUsuario(String id) {
        this.consultarId(id);
        this.repo.deleteById(id);
    }

    public void deletarTodos() {
        this.repo.deleteAll();
    }


}
