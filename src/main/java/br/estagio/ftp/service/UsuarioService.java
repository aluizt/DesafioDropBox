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
        return repo.findAll();
    }


    public Page<Usuario> listarTodosPaginado(int count, int page) {

        Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nomeUsuario");

        return repo.findAll(pages);
    }


    public Usuario consultarId(String id)  {

        Optional<Usuario> optional = this.repo.findById(id);


        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("asd"));
    }


    public Usuario incluirUsuario(Usuario usuario) {
        return this.repo.save(usuario);
    }

    public Usuario alterarUsuario(Usuario u)  {

        Usuario usuario = this.consultarId(u.getIdUsuario());

        if (usuario == null) {
            return null;
        }
        usuario = this.repo.save(u);

        return usuario;
    }

    public void deletarUsuario(String id) {
        this.repo.deleteById(id);
    }

    public void deletarTodos() {
        this.repo.deleteAll();
    }


}
