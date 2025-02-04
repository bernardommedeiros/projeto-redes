package org.projetoredes.models;

import lombok.Data;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

@Data // getter, setter, hashcode e equals
public class Server {
    private static ServerSocket serverSocket;
    private static SocketAddress serverAddress;
    private static InetAddress host;
    private static int port;
    private static int connQueueSize;


    private static void run() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        while(!keyboard.nextLine().equals("exit")){
            serverSocket.accept();
        }
        serverSocket.close();
    }



    public void startServer() throws IOException {
        if(Server.serverAddress == null){
            // TODO - Criar excessao propria
            throw new RuntimeException("Erro ao iniciar servidor: nenhum endpoint especificado.");
        }

        Server.serverSocket = new ServerSocket(Server.port, Server.connQueueSize, Server.host);

        Server.run();
    }

    public void startServer(String host, int port, int connQueueSize) throws IOException {
        try {
            Server.host = InetAddress.getByName(host);
        }catch (UnknownHostException error){
            // TODO - criar gerenciamento de excessoes
            throw new RuntimeException("Error in server setup: " + error.getMessage());
        }
        Server.port = port;
        Server.connQueueSize = connQueueSize;
        Server.serverAddress = new InetSocketAddress(host, port);
        Server.serverSocket = new ServerSocket(Server.port, Server.connQueueSize, Server.host);

        Server.run();
    }

    public void serverSetup(String host, int port, int connQueueSize) throws IOException {
        try {
            Server.host = InetAddress.getByName(host);
        }catch (UnknownHostException error){
            // TODO - criar gerenciamento de excessoes
            throw new RuntimeException("Error in server setup: " + error.getMessage());
        }
        Server.port = port;
        Server.connQueueSize = connQueueSize;
        Server.serverAddress = new InetSocketAddress(Server.host, Server.port);
        Server.serverSocket = new ServerSocket(Server.port, Server.connQueueSize, Server.host);
    }
}
