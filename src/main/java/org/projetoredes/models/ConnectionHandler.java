package org.projetoredes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public class ConnectionHandler extends Thread{
    Socket clientSocket;

    @Override
    public void run(){
        while(clientSocket.isConnected()){
            try(InputStream clientIS = clientSocket.getInputStream();
                OutputStream clientOS = clientSocket.getOutputStream();
            ){
                byte[] msg = ("Digite um comando:").getBytes(StandardCharsets.UTF_8);
                clientOS.write(msg);

                byte[] received = new byte[128];
                int bytesRead = clientIS.read(received);
                System.out.println(bytesRead + " bytes lidos: " + Arrays.toString(received));

            }catch (IOException e){
                throw new RuntimeException("Error with client IO stream: ", e);
            }
        }
    }

    private void getCommand(){}

}
