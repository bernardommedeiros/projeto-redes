package org.projetoredes.classes;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

@Getter
@Setter
public class Server {
    private ServerSocket serverSocket;   // socket do servidor
    private InetAddress host;            // ip do servidor
    private int port;                    // porta do servidor
    private int connQueueSize;           // Quantos clientes podem ter


    public void startServer() throws IOException {
        Scanner keyboard = new Scanner(System.in);

        while(!keyboard.nextLine().equals("exit")){
            // Aceita conexoes
            Socket client = serverSocket.accept();

            // Cria uma Thread para o cliente
            new ConnectionHandler(client).start();
        }

        serverSocket.close();
    }

    public Server(String host, int port, int connQueueSize){
        try{
            this.host = InetAddress.getByName(host); // Pega o InetAddress por meio do String do IP
        }catch (UnknownHostException error){
            throw new RuntimeException("Host inválido: " + error);
        }

        this.port = port;
        this.connQueueSize = connQueueSize;

        try{
            // Cria o socket
            this.serverSocket = new ServerSocket(this.port, this.connQueueSize, this.host);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o socket do servidor: " + e);
        }
    }
}
