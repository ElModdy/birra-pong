package ping_pong_server;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class RequestHandler implements Runnable{
    // Il server che ha smistato il pacchetto
    private final Server server;
    // La porta dalla quale il pacchetto é stato inviato
    private final int port;
    // Il buffer del pacchetto ricevuto contenente la "richiesta"
    private final byte[] buf;
    // L'indirizzo dal quale il pacchetto é stato inviato
    private final InetAddress address;
    
    public RequestHandler(DatagramPacket packet, Server server) {
        address = packet.getAddress();
        port = packet.getPort();
        buf = packet.getData();
        this.server = server;
    }
    
    // Metodo che viene chiamato appena il thread é stato creato
    @Override
    public void run() {
        // Se il contenuto del pacchetto é uguale a ping
        // N.B: Poiché il messaggio contenuto all'interno del buffer
        // non lo occupa completamente, la risultante dalla conversione in
        // String sará una stringa composta dal messagio ( es. "ping" )
        // e da N caratteri ( corrispondenti allo 0 nella tabella ASCII )
        // quanti sono i byte rimanenti nel buffer.
        if(new String(buf, 0, buf.length).trim().equals("ping")){
            // Assegna al buffer il valore di "contempo" nell'equivalente in bytes
            byte[] response = "contempo".getBytes();
            
            // Chiama il metodo sendPacket del Server che ha ricevuto la richiesta
            // passandogli il pacchetto contenente la risposta.
            server.sendPacket(new DatagramPacket(response,
                                                 response.length,
                                                 address,
                                                 port));
        }
    }
}
