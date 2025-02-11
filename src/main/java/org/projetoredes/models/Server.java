package org.projetoredes.models;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

@Getter
@Setter
public class Server {
    private ServerSocket serverSocket;
    private SocketAddress serverAddress;
    private InetAddress host;
    private int port;
    private int connQueueSize;


    public void startServer() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        while(!keyboard.nextLine().equals("exit")){
            Socket client = serverSocket.accept();

            // Cria uma Thread para o cliente
            new ConnectionHandler(client).start();
        }

        serverSocket.close();
    }

    public Server(String host, int port, int connQueueSize){
        try{
            this.host = InetAddress.getByName(host);
        }catch (UnknownHostException error){
            throw new RuntimeException("Host inválido: " + error);
        }

        this.port = port;
        this.connQueueSize = connQueueSize;
        this.serverAddress = new InetSocketAddress(host, port);

        try{
            this.serverSocket = new ServerSocket(this.port, this.connQueueSize, this.host);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o socket do servidor: " + e);
        }
    }
}
