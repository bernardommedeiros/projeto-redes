package com.projetoredes.abstractions;

public interface ClientSocket {
    // ajustar retornos depois
    void connectServer();
    void getProcessorsQuantity();
    void getFreeRam();
    void getFreeDisk();
    void getProcessorTemperature();
}
