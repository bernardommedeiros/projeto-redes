package org.projetoredes.classes;

import org.projetoredes.abstractions.ClientSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements ClientSocket {
    private Socket clientSocket;
    private InetAddress host;
    private int port;


    private void run(){
        try{
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            Scanner cmdInput = new Scanner(System.in);

            while(true){
                byte[] reader = new byte[128];

                //le a qtd de dados recebidos
                int inputRead = input.read(reader);
                if(inputRead != -1){
                    System.out.println(new String(reader, 0, inputRead, StandardCharsets.UTF_8));

                    byte[] cmdInputBytes = cmdInput.nextLine().getBytes(StandardCharsets.UTF_8);
                    output.write(cmdInputBytes);
                }
            }
        }catch (IOException e){
            throw new RuntimeException("Erro ao conectar no servidor: ", e);
        }
    }


    @Override
    public void connectServer(String host, int port) {
        try{
            this.host = InetAddress.getByName(host); //Determines the IP address of a host, given the host's name.
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
