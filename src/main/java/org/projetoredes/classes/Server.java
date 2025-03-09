package org.projetoredes.classes;

import lombok.Getter;
import lombok.Setter;
import oshi.util.tuples.Pair;

import java.io.IOException;
import java.net.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Getter
@Setter
public class Server {
    private ServerSocket serverSocket;   // socket do servidor
    private InetAddress host;            // ip do servidor
    private int port;                    // porta do servidor
    private int connQueueSize;           // Quantos clientes podem ter
    private ServerReceiveConnThread serverReceiveConnThread;

    public void startServer() throws IOException {
        this.serverReceiveConnThread = new ServerReceiveConnThread(this.serverSocket);
        serverReceiveConnThread.start();
        new CheckConnectionsHandler(this.serverReceiveConnThread).start();

        Scanner keyboard = new Scanner(System.in);

        System.out.println("!help para mostrar todos comandos.");
        System.out.print("digite um comando:  ");
        String command;
        while(!(command = keyboard.nextLine()).equals("exit")){
            Pair<String, Integer> resolvedCommand = resolveCommand(command);
            String actualCommand = resolvedCommand.getA();
            int clientId = resolvedCommand.getB();

            if(clientId == -1) {
                System.out.println("Cliente invalido");
            }
            else if(actualCommand.isEmpty()) {
                System.out.println("Comando invalido");
            }
            else if(!actualCommand.equals("other")){
                ConnectionHandler client = serverReceiveConnThread.getConnectedClients().get(clientId);
                client.sendCommand(actualCommand);
            }

            System.out.println("\n\n!help para mostrar todos comandos.");
            System.out.print("digite um comando:  ");
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




    private Pair<String, Integer> resolveCommand(String command){
        Pair<String, Integer> resolved = new Pair<>("", -1);

        if(command.contains("!help")){
            System.out.println("\n\nUtilize:  !'comando' {numero do cliente}\n" +
                    "Comandos disponiveis:\n" +
                    "!clients  = retorna uma lista dos clientes conectados\n" +
                    "!processor  = quantidade de nucleos de processador\n" +
                    "!freeram  = quantidade de memoria ram livre\n" +
                    "!freedisk  = espaço disponivel no disco\n" +
                    "!temp  = temperatura do processador\n" +
                    "!media  = media dos dados"
            );

            resolved = new Pair<>("other", -2);
        }
        else if(command.contains("!clients")){
            Map<Integer, ConnectionHandler> clients = serverReceiveConnThread.getConnectedClients();
            for(Map.Entry<Integer, ConnectionHandler> client : clients.entrySet()){
                System.out.println("==========================\n" +
                        "Cliente: " + client.getValue().getClientSocket().getInetAddress() + "\n" +
                        "ID: " + client.getKey() + "\n" +
                        "==========================\n"
                );
            }

            resolved = new Pair<>("other", -2);
        }
        else{
            int client = -1;

            for(int i = command.length()-1; i > -1; i--){
                if(Character.isDigit(command.charAt(i))){
                    client = command.charAt(i) - '0';
                }
            }

            if(client != -1) {
                StringBuilder actualCommand = new StringBuilder();
                for (int i = 0; i < command.length(); i++) {
                    if (Character.isAlphabetic(command.charAt(i)) && command.charAt(i) != '!') {
                        actualCommand.append(command.charAt(i));
                    }
                }

                resolved = switch (actualCommand.toString()) {
                    case "processor" -> new Pair<>("processor", client);
                    case "freeram" -> new Pair<>("freeram", client);
                    case "freedisk" -> new Pair<>("freedisk", client);
                    case "temp" -> new Pair<>("temp", client);
                    case "media" -> new Pair<>("media", client);
                    default -> resolved;
                };
            }
        }

        return resolved;
    }
}
