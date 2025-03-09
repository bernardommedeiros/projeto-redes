package org.projetoredes.classes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.projetoredes.util.Encryptor;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

// Essa classe representa a thread que sera feita para cada cliente conectado

@Getter
@RequiredArgsConstructor
public class ConnectionHandler extends Thread {
    private final Socket clientSocket;
    private SecretKey key;
    private InputStream clientIS;
    private OutputStream clientOS;

    @Override
    public void run() {
        try {
            this.clientIS = clientSocket.getInputStream();   // Entrada de dados vindo do cliente
            this.clientOS = clientSocket.getOutputStream(); // Envio de dados para o cliente
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            this.key = Encryptor.genKey();
            clientOS.write(Encryptor.prepareKey(key));
            clientOS.flush();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar chave para cliente: ", e);
        }
    }


    public void sendCommand(String command){
        // define a mensagem a ser enviada
        byte[] commandBytes = Encryptor.encrypt(command, key);

        try {
            // envia a mensagem
            clientOS.write(commandBytes);
            // limpa o buffer, obriga o cliente a processar o dado
            clientOS.flush();

            // recebimento da resposta
            byte[] response = new byte[32];
            int bytesRead = clientIS.read(response);

            if(bytesRead > 0){
                // Descriptografia da resposta
                byte[] data = Arrays.copyOfRange(response, 0, bytesRead);
                String decryptedResponse = Encryptor.decrypt(data, key);

                System.out.println(decryptedResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar comando pro cliente: " + e);
        }
    }


    public boolean checkConnection(){
        byte[] commandBytes = Encryptor.encrypt("connectiontest", key);

        try {
            clientOS.write(commandBytes);
            clientOS.flush();

            int bytesRead = clientIS.read();
            return bytesRead > 0;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar checagem de conexao pro cliente: " + e);
        }
    }
}
