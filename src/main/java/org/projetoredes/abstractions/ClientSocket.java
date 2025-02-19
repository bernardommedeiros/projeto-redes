package org.projetoredes.abstractions;

public interface ClientSocket {
    // ajustar retornos depois
    void connectServer(String host, int port);
    int getProcessorsQuantity();
    double getFreeRam();
    double getFreeDisk();
    double getProcessorTemperature();
}
