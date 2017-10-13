package ping_pong_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] ping = "ping".getBytes();
        byte[] buf = new byte[512];
        System.arraycopy(ping, 0, buf, 0, ping.length);
        DatagramPacket packet = new DatagramPacket(buf,
                                                   buf.length,
                                                   InetAddress.getByName("127.0.0.1"),
                                                   7777);
        socket.send(packet);
        socket.receive(packet);
        System.out.println("Risposta: " + new String(packet.getData(), 
                                                     0,
                                                     packet.getLength()));
        socket.close();
    }

}
