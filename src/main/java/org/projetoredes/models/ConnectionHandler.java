package org.projetoredes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Getter
@AllArgsConstructor
public class ConnectionHandler extends Thread{
    Socket clientSocket;

    @Override
    public void run(){
    }


}
