package org.projetoredes;

import org.projetoredes.classes.Server;

import java.io.IOException;

public class RunServer {
    public static void main(String[] args) throws IOException {
        Server server = new Server("localhost", 6000, 5);

        server.startServer();
    }
}