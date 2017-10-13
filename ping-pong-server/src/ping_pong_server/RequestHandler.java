package ping_pong_server;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class RequestHandler implements Runnable{
    private final Server server;
    private final int port;
    private final byte[] buf;
    private final InetAddress address;
    
    public RequestHandler(DatagramPacket packet, Server server) {
        address = packet.getAddress();
        port = packet.getPort();
        buf = packet.getData();
        this.server = server;
    }
    
    @Override
    public void run() {
        if(new String(buf, 0, buf.length).trim().equals("ping")){
            byte[] response = "contempo".getBytes();
            server.sendPacket(new DatagramPacket(response,
                                                 response.length,
                                                 address,
                                                 port));
        }
    }
}
