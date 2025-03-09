package org.projetoredes.classes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Getter
public class ServerReceiveConnThread extends Thread{
    private final ServerSocket serverSocket;
    private Map<Integer, ConnectionHandler> connectedClients = new ConcurrentHashMap<>();
    private static boolean running = false;

    @Override
    public void run(){
        if(!running){
            running = true;

            int counter = 0;
            while(true){
                try{
                    // Aceita conexoes
                    Socket client = serverSocket.accept();

                    // Cria uma Thread para o cliente
                    ConnectionHandler newConnectionHandler = new ConnectionHandler(client);
                    newConnectionHandler.start();

                    // Adiciona o handler a lista
                    connectedClients.put(counter, newConnectionHandler);

                    System.out.println("\nConexao iniciada com " + client.getInetAddress().getHostAddress() + ":" + client.getPort() + " - id do cliente: " + counter);
                    System.out.println("\n!help para mostrar todos comandos.");
                    System.out.print("digite um comando:  ");

                    counter++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
