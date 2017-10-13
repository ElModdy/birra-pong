package ping_pong_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static final int PORT = 7777;
    static final int BUF_LENGTH = 512;
    private static final int THREAD_POOL = 4;
    private DatagramSocket socket;

    public Server() throws SocketException {
        socket = new DatagramSocket(PORT);
    }
    
    public void start() throws IOException{
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        byte[] buf = new byte[BUF_LENGTH];
        DatagramPacket packet = new DatagramPacket(buf, BUF_LENGTH);
        
        while (true) {
            socket.receive(packet);
            executor.execute(new RequestHandler(packet, this));
        }
    }
        
    public void sendPacket(DatagramPacket packet){
        try {
            socket.send(packet);
        } catch (IOException ex) {}
    }
    
    public static void main(String[] args) throws Exception {
        new Server().start();
    }
}
