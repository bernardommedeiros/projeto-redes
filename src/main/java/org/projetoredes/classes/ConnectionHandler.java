package org.projetoredes.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.projetoredes.util.Encryptor;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

// Essa classe representa a thread que sera feita para cada cliente conectado

@Getter
@AllArgsConstructor
public class ConnectionHandler extends Thread {
    Socket clientSocket;

    @Override
    public void run() {
        try (InputStream clientIS = clientSocket.getInputStream();   // Entrada de dados vindo do cliente
             OutputStream clientOS = clientSocket.getOutputStream(); // Envio de dados para o cliente
        ) {
            while (clientSocket.isConnected()) {
                // define a mensagem a ser enviada
                byte[] msg = Encryptor.encrypt("Teste");
                // envia a mensagem
                clientOS.write(msg);
                // limpa o buffer, obriga o cliente a processar o dado
                clientOS.flush();

                byte[] received = new byte[256]; // buffer pra armazenar a informaçao que veio do cliente

                int bytesRead = clientIS.read(received); // bytesRead = qtd de bytes lidos, received <- informaçao

                byte[] decryptedMsg = Encryptor.decrypt(received);
                String commandReceived = new String(decryptedMsg, 0, bytesRead, StandardCharsets.UTF_8);
                System.out.println(bytesRead + " bytes lidos: " + bytesRead + " - " + commandReceived);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro na comunicacao com o cliente: ", e);
        }
    }
}
