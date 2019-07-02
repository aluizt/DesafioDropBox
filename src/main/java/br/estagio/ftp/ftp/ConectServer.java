package br.estagio.ftp.ftp;

import br.estagio.ftp.service.exception.ErroFtpException;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ConectServer {

    public FTPClient conectarServer(){
        String server = "localhost";
        int port = 21;
        String user = "ftp";                      // admin
        String pass = "estagio";                  // 123

        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

        try {
            ftp.connect(server, port);

            showServerReply(ftp);

            // Após a tentativa de conexão, você deve verificar o código de resposta para verificar
            // sucesso. Se você deseja acessar o código de resposta exato do FTP causando um sucesso
            // ou uma falha, você deve ligar getReplyCode após um sucesso ou falha.

            int replyCode = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new ErroFtpException("Operação falhou. Server reply code: "+replyCode);
            }

            boolean success = ftp.login(user, pass);
            showServerReply(ftp);

            if (!success) {
                throw new ErroFtpException("Erro de login ");
            } else {
                System.out.println("LOGADO NO SERVIDOR FTP");
            }


        } catch (IOException ex) {
            System.err.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }

        return ftp;
    }

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

}
