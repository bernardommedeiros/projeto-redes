package org.projetoredes.classes;

import org.projetoredes.abstractions.ClientCommandsHandler;
import org.projetoredes.util.Encryptor;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client extends ClientCommandsHandler {
    private Socket clientSocket;
    private InetAddress host;
    private int port;
    private SecretKey key;


    private void run(){
        try{
            InputStream input = clientSocket.getInputStream();    // Entrada de dados vindo do servidor
            OutputStream output = clientSocket.getOutputStream(); // Saida de dados para o servidor

            this.receiveKey(input);
            while(true){
                byte[] reader = new byte[32]; // 32 = quantidade de bytes a ser recebida a cada processamento

                // le a qtd de dados recebidos
                int inputRead = input.read(reader); // inputRead = qtd de bytes lidos ; reader <- bytes vindo do servidor (informaçao em si)
                byte[] data = Arrays.copyOfRange(reader, 0, inputRead);
                String decryptedMsg = Encryptor.decrypt(data, key);

                // envio da resposta
                String commandResult = manageCommands(decryptedMsg);
                byte[] commandResultBytes = Encryptor.encrypt(commandResult, key);
                output.write(commandResultBytes);
                output.flush();
            }
        }catch (IOException e){
            throw new RuntimeException("Erro ao conectar no servidor: ", e);
        }
    }

    private void receiveKey(InputStream clientIS){
        byte[] buffer = new byte[32];

        try{
            clientIS.read(buffer);
            this.key = Encryptor.resolveKey(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void connectServer(String host, int port) {
        try{
            this.host = InetAddress.getByName(host); // Transforma o IP de string para InetAddress
            this.port = port;
        } catch (UnknownHostException e) {
            throw new RuntimeException("Erro ao definir host: ", e);
        }

        try{
             this.clientSocket = new Socket(host, port);
             this.run();
        }catch (IOException e){
            throw new RuntimeException("Erro ao conectar no servidor: ", e);
        }
    }
}
