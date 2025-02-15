package org.projetoredes.abstractions;

public interface ClientSocket {
    // ajustar retornos depois
    void connectServer(String host, int port);

    void getProcessorsQuantity();
    void getFreeRam();
    void getFreeDisk();
    void getProcessorTemperature();
}
