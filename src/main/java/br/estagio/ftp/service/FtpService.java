package br.estagio.ftp.service;

import br.estagio.ftp.model.Usuario;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FtpService {

    public FTPClient verificaDiretorio(String id, FTPClient ftpClient){

        boolean direorioExiste= false;

        try {

            FTPFile[] listaDiretorios = ftpClient.listDirectories();

            for(FTPFile f:listaDiretorios){
                if(f.getName().equals(id)){
                    direorioExiste=true;
                }
            }

            if(direorioExiste==false) {
                if (ftpClient.makeDirectory(id)) {
                    System.out.println("diretorio criado");
                } else System.out.println("diretorio nao criado");
            }

            return alteraDiretorio(ftpClient, id);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private FTPClient alteraDiretorio(FTPClient ftpClient, String id){

        try {
           if(ftpClient.changeWorkingDirectory(id)){
               System.out.println("trocou de diretorio "+id);

           }else System.out.println("não troucou de diretorio");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  ftpClient;
    }
}
