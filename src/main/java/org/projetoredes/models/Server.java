package org.projetoredes.models;

import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Setter
public class Server {
    private static InetAddress host;
    private static int port;


    public Server(String host, int port) throws UnknownHostException {
        Server.host = InetAddress.getByName(host);
        Server.port = port;
    }
}
