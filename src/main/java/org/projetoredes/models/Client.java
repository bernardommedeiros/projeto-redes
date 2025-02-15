package org.projetoredes.models;

import org.projetoredes.abstractions.ClientSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;

public class Client implements ClientSocket {
    private SocketAddress socket;
    private InputStream dataInput;
    private OutputStream dataOutput;


    
    @Override
    public void connectServer(String host, int port) {
        InetAddress address = InetAddress.getByName(host); //Determines the IP address of a host, given the host's name.
        
        
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
