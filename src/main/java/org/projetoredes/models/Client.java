package org.projetoredes.models;

import com.projetoredes.abstractions.ClientSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;

public class Client implements ClientSocket {
    private SocketAddress socket;
    private InputStream dataInput;
    private OutputStream dataOutput;







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
