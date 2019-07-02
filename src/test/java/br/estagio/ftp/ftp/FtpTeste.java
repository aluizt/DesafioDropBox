package br.estagio.ftp.ftp;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Assert;
import org.junit.Test;

public class FtpTeste {


    @Test
    public void testaConexao(){

        ConectServer conectServer = new ConectServer();
        FTPClient ftpClient;

        //ação

        ftpClient = conectServer.conectarServer();

        int replyCode = ftpClient.getReplyCode();

        // verificação

        Assert.assertEquals(true,FTPReply.isPositiveCompletion(replyCode));
    }


}
