package org.projetoredes.models;

import lombok.Data;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

@Data // getter, setter, hashcode e equals
public class Server {
    private ServerSocket serverSocket;
    private SocketAddress serverAddress;
    private InetAddress host;
    private int port;
    private int connQueueSize;


    private void run() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        while(!keyboard.nextLine().equals("exit")){
            serverSocket.accept();

            Thread.
        }
        serverSocket.close();
    }



    public void startServer(String host, int port, int connQueueSize) throws IOException {
        try {
            this.host = InetAddress.getByName(host);
        }catch (UnknownHostException error){
            // TODO - criar gerenciamento de excessoes
            throw new RuntimeException("Error in server setup: " + error.getMessage());
        }
        this.port = port;
        this.connQueueSize = connQueueSize;
        this.serverAddress = new InetSocketAddress(host, port);
        this.serverSocket = new ServerSocket(this.port, this.connQueueSize, this.host);

        run();
    }
}
