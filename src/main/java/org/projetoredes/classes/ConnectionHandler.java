package org.projetoredes.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.projetoredes.abstractions.ClientHandler;
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

@Getter
@AllArgsConstructor
public class ConnectionHandler extends Thread implements ClientHandler {
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
                String commandReceived = new String(received, 0, bytesRead, StandardCharsets.UTF_8);
                System.out.println(bytesRead + " bytes lidos: " + bytesRead + " - " + commandReceived);

                System.out.println(manageCommands(commandReceived));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro na comunicacao com o cliente: ", e);
        }
    }

    private String manageCommands(String command){
        switch(command){
            case "processor":
                return String.valueOf(getProcessorsQuantity());
            case "freeram":
                return String.format("%.1f", getFreeRam());
            case "freedisk":
                return String.valueOf(getFreeDisk());
            default:
                return "Command not found";
        }
    }


    @Override
    public int getProcessorsQuantity() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        return processor.getPhysicalProcessorCount();
    }

    @Override
    public double getFreeRam() {
        SystemInfo si = new SystemInfo();
        long ramBytesAvailable = si.getHardware().getMemory().getAvailable();
        return (ramBytesAvailable / 1073741824.0);
    }

    @Override
    public double getFreeDisk() {
        SystemInfo si = new SystemInfo();

        List<OSFileStore> fileSystem = si.getOperatingSystem().getFileSystem().getFileStores();
        long freeDiskBytes = 0;
        for(OSFileStore store : fileSystem){
            if(store.getMount().charAt(0) == 'C'){
                freeDiskBytes += store.getUsableSpace();
            }
        }

        return (freeDiskBytes / 1073741824.0);
    }

    @Override
    public void getProcessorTemperature() {

    }
}
