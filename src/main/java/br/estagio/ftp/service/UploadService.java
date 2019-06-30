package br.estagio.ftp.service;

import br.estagio.ftp.ftp.ConectServer;
import br.estagio.ftp.model.Amigos;
import br.estagio.ftp.model.Arquivos;
import br.estagio.ftp.model.Usuario;
import br.estagio.ftp.service.exception.ObjetoNaoEncontradoException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {


    private UsuarioService usuarioService;
    private AmigoService amigoService;
    private DiscoService discoService;
    private ConectServer conectServer;
    private FtpService ftpService;

    @Autowired
    public UploadService(UsuarioService usuarioService,
                         AmigoService amigoService,
                         DiscoService discoService,
                         ConectServer conectServer,
                         FtpService ftpService) {
        this.usuarioService = usuarioService;
        this.amigoService = amigoService;
        this.discoService = discoService;
        this.conectServer = conectServer;
        this.ftpService = ftpService;
    }

    public void upload(MultipartFile arquivo, Usuario usuario){

        FTPClient ftpClient = this.conectServer.conectarServer();
        ftpClient.enterLocalPassiveMode();
        ftpClient=this.ftpService.verificaDiretorio(usuario.getIdUsuario(),ftpClient);

        try {

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            try {
                ftpClient.storeFile(arquivo.getOriginalFilename(),arquivo.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                ftpClient.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FTPFile[] listarUploads(String id){

        Usuario usuario = this.usuarioService.consultarId(id);

        FTPClient ftpClient = this.conectServer.conectarServer();
        ftpClient.enterLocalPassiveMode();
        ftpClient=this.ftpService.verificaDiretorio(usuario.getIdUsuario(),ftpClient);


        try {
            FTPListParseEngine engine = ftpClient.initiateListParsing(ftpClient.printWorkingDirectory());
            FTPFile[] files=null;
            while (engine.hasNext()){
                files = engine.getNext(3);
            }
            return files;

        } catch (IOException e) {
            e.printStackTrace();
        }
        //try {
        //    return ftpClient.listFiles();
        //} catch (IOException e) {
        //    e.printStackTrace();
       // }

        return  null;
    }


    public Usuario localizarId(String id) {
        return this.usuarioService.consultarId(id);
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
