package org.projetoredes.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Getter
@AllArgsConstructor
public class ConnectionHandler extends Thread {
    Socket clientSocket;

    @Override
    public void run() {
        try (InputStream clientIS = clientSocket.getInputStream();
             OutputStream clientOS = clientSocket.getOutputStream();
        ) {
            while (clientSocket.isConnected()) {
                byte[] msg = ("Digite um comando:").getBytes(StandardCharsets.UTF_8);
                clientOS.write(msg);
                clientOS.flush();

                byte[] received = new byte[16];
                int bytesRead = clientIS.read(received);
                System.out.println(bytesRead + " bytes lidos: " + bytesRead + " - " + new String(received, 0, bytesRead, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro na comunicacao com o cliente: ", e);
        }
    }

    private void getCommand(){}

}
