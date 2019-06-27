package br.estagio.ftp.service;

import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.Arquivos;
import br.estagio.ftp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AmigoService amigoService;

    @Autowired
    private DiscoService discoService;

    public Usuario localizarId(String id) {
        Usuario usuario=this.usuarioService.consultarId(id);
        if(usuario==null){
            return null;
        }
        return usuario;
    }


    public List<Arquivos> localizarArquivosCompartilhados(String id)  {

        List<Amigos> listaAmigos = this.amigoService.listarTodosAmigos(id);

        if(listaAmigos == null){
            return null;
        }
        List<Arquivos> arquivos = new ArrayList<>();

        Arquivos arquivosRecebidos = new Arquivos();

        for (Amigos a:listaAmigos){

            arquivosRecebidos=this.discoService.listarArquivos(a.getIdAmigo());

            if(arquivosRecebidos !=null) {
                if (arquivosRecebidos.getArquivos() != null) {

                    Arquivos arquivosCompartilhados = new Arquivos();
                    arquivosCompartilhados.setId(a.getIdAmigo());
                    arquivosCompartilhados.setArquivos(arquivosRecebidos.getArquivos());
                    arquivos.add(arquivosCompartilhados);
                }
            }
        }

        return arquivos;
    }
}
