package org.projetoredes.models;

import org.projetoredes.abstractions.ClientSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Client implements ClientSocket {
    private SocketAddress socket;
    private InputStream dataInput;
    private OutputStream dataOutput;


    @Override
    public void connectServer(String host, int port) {
        try{
            InetAddress address = InetAddress.getByName(host); //Determines the IP address of a host, given the host's name.
        } catch (UnknownHostException e) {
            throw new RuntimeException("Erro ao definir host: ", e);
        }

        try{
            Socket socketClient = new Socket(host, port);

            InputStream input = socketClient.getInputStream();
            byte[] reader = new byte[128];

            //le a qtd de dados recebidos
            int inputRead = input.read(reader);
            System.out.println(new String(reader, StandardCharsets.UTF_8));

            Scanner cmdInput = new Scanner(System.in);
            byte[] cmdInputBytes = cmdInput.next().getBytes(StandardCharsets.UTF_8);

            OutputStream output = socketClient.getOutputStream();
            output.write(cmdInputBytes);
        }catch (IOException e){
            throw new RuntimeException("Erro ao conectar no servidor: ", e);
        }
    }

    @Override
    public void getProcessorsQuantity() {

    }

    @Override
    public void getFreeRam() {

    }

    @Override
    public void getFreeDisk() {

    }

    @Override
    public void getProcessorTemperature() {

    }
}
