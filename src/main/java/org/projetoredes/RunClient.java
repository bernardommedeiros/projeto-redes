package org.projetoredes;

import org.projetoredes.classes.Client;

public class RunClient {
    public static void main(String[] args) {
        Client client = new Client();

        client.connectServer("localhost", 6000);

    }
}