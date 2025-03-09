package org.projetoredes.abstractions;

public interface ClientCommands {
    // ajustar retornos depois
    void connectServer(String host, int port);
    int getProcessorsQuantity();
    double getFreeRam();
    double getFreeDisk();
    double getProcessorTemperature();
    double getMedia();
}
