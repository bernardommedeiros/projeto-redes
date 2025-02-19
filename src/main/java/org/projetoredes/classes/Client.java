package org.projetoredes.classes;

import org.projetoredes.abstractions.ClientCommandsHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends ClientCommandsHandler {
    private Socket clientSocket;
    private InetAddress host;
    private int port;


    private void run(){
        try{
            InputStream input = clientSocket.getInputStream();    // Entrada de dados vindo do servidor
            OutputStream output = clientSocket.getOutputStream(); // Saida de dados para o servidor
            Scanner cmdInput = new Scanner(System.in);

            while(true){
                byte[] reader = new byte[32]; // 32 = quantidade de bytes a ser recebida a cada processamento

                // le a qtd de dados recebidos
                int inputRead = input.read(reader); // inputRead = qtd de bytes lidos ; reader <- bytes vindo do servidor (informaçao em si)

                // Loop para ler toda a informaçao, -1 significa que nao tem mais nada no buffer do InputStream (nao tem mais informaçao a ser lida)
                if(inputRead != -1){
                    System.out.println(new String(reader, 0, inputRead, StandardCharsets.UTF_8));
                }

                // Enviar comandos para o servidor
                // TODO - Verificar se o comando e valido antes de enviar
                // TODO - Receber comandos e enviar dados pro servidor
                byte[] cmdInputBytes = cmdInput.nextLine().getBytes(StandardCharsets.UTF_8); // String para byte[] em UTF_8
                output.write(cmdInputBytes); // envia a informaçao para o servidor
            }
        }catch (IOException e){
            throw new RuntimeException("Erro ao conectar no servidor: ", e);
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
