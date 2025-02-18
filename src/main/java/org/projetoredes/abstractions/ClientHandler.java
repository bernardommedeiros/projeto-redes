package org.projetoredes.abstractions;

public interface ClientHandler {
    int getProcessorsQuantity();
    double getFreeRam();
    double getFreeDisk();
    void getProcessorTemperature();
}
