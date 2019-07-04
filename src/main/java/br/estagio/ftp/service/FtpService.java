package br.estagio.ftp.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FtpService {

    private static final Logger logger = LoggerFactory.getLogger(FtpService.class);

    public FTPClient verificaDiretorio(String id, FTPClient ftpClient){

        boolean direorioExiste= false;

        try {

            FTPFile[] listaDiretorios = ftpClient.listDirectories();

            for(FTPFile f:listaDiretorios){
                if(f.getName().equals(id)){
                    direorioExiste=true;
                }
            }

            if(!direorioExiste) {
                if (ftpClient.makeDirectory(id)) {
                    logger.info("Diretorio criado");
                } else logger.info("Diretorio nao criado");
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
               String log = "Trocou de diretorio "+id;
               logger.info(log);

           }else logger.info("não troucou de diretorio");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  ftpClient;
    }
}
