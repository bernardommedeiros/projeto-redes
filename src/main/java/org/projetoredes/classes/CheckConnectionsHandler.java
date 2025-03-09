package org.projetoredes.classes;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;


@RequiredArgsConstructor
public class CheckConnectionsHandler extends Thread {
    private final ServerReceiveConnThread serverReceiveConnThread;
    private static boolean running = false;
    private Instant lastConnectionTest = null;

    @Override
    public void run(){
        if(!running) {
            running = true;

            while (true) {
                Map<Integer, ConnectionHandler> connectedClients = serverReceiveConnThread.getConnectedClients();
                Instant actualTest = Instant.now();
                if(lastConnectionTest == null || actualTest.getEpochSecond() - lastConnectionTest.getEpochSecond() > 30){
                    for(Map.Entry<Integer, ConnectionHandler> client : connectedClients.entrySet()){
                        if(!client.getValue().checkConnection()){
                            connectedClients.remove(client.getKey());
                        };
                    }

                    this.lastConnectionTest = actualTest;
                }
            }
        }
    }
}
