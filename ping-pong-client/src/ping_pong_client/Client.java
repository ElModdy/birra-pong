package ping_pong_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {
        // Creazione istanza del DatagramSocket ( per trasferimento pacchetti in UDP )
        // che ci permetterá di contattare il server
        DatagramSocket socket = new DatagramSocket();
        
        // Creazione del buffer, che contestualizzato, rappresenta il contenuto
        //  del pacchetto in byte.
        byte[] buf = new byte[512];
        
        // Conversione di ping nell'equivalente in binario
        byte[] ping = "ping".getBytes();
        // Inserimento di ping nelle prime posizioni del buffer
        System.arraycopy(ping, 0, buf, 0, ping.length);
        
        // Creazione del pacchetto
        DatagramPacket packet = new DatagramPacket(buf, // buffer
                                                   buf.length, // dimensione del buffer
                                                   InetAddress.getByName("127.0.0.1"), // indirizzo ip di destinazione
                                                   7777); // porta di destinazione ( sulla quale il server sará in ascolto )
        // Invio pacchetto
        socket.send(packet);
        
        // Metodo che blocca l'esecuzione fino all'arrivo di un pacchetto al socket
        // Il pacchetto sul quale verrá scritto é passato come parametro
        socket.receive(packet);
        
        // Stampa il contenuto del pacchetto ( bytes ) dopo averlo convertito in String
        System.out.println("Risposta: " + new String(packet.getData(), 
                                                     0,
                                                     packet.getLength()));
        
        // Chiude il socket
        socket.close();
    }

}
