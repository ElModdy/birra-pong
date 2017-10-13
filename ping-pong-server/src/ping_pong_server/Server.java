package ping_pong_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // Porta sul quale il server ascolterá
    static final int PORT = 7777;
    // Dimensione del buffer
    static final int BUF_LENGTH = 512;
    // Numero massimo di thread in esecuzione contemporaneamente
    private static final int THREAD_POOL = 4;
    // Socket dal quale il server ascolterá e risponderá
    private DatagramSocket socket;

    // Costruttore
    public Server() throws SocketException {
        // Creazione istanza del socket sulla porta specificata
        socket = new DatagramSocket(PORT);
    }
    
    // Metodo che viene invocato per inizializzare il server
    public void start() throws IOException{
        // Creazione dell'esecutore che si occuperá di gestire la creazione,
        //  la distruzione, e il riciclaggio dei thread
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        
        // Creazione del buffer della dimensione indicata
        byte[] buf = new byte[BUF_LENGTH];
        
        // Creazione del pacchetto
        DatagramPacket packet = new DatagramPacket(buf, BUF_LENGTH);
        
        // Il server sará in ascolto per "sempre"
        while (true) {
            // Metodo che blocca l'esecuzione fino all'arrivo di un pacchetto al socket
            // Il pacchetto sul quale verrá scritto é passato come parametro
            socket.receive(packet);
            
            // Chiamata al metodo execute dell'executor il quale,
            //  se il numero di thread giá in esecuzione é inferiore a quello
            //  indicato nel costruttore, si proccupa di creare un nuovo thread, 
            //  o di riciclarne uno giá esistente.
            //  Come parametro si aspetta un Runnable ( interfaccia ), ovvero
            //  una classe che implementa il metodo run il quale verrá chiamato
            //  alla creazione del thread.
            //  La classe in questione é RequestHandler la quale si aspetta come
            //  parametri il pacchetto ricevuto e il server che lo ha ricevuto
            //  ovvero this.
            executor.execute(new RequestHandler(packet, this));
        }
    }
    
    // Questo metodo verrá chiamato per rispondere alle richieste ricevute.
    // Piuttosto che instanziare un socket per ogni richiesta ricevuto, ovvero
    // per ogni thread, ho preferito rispondere dal socket che ha ricevuto la 
    // richesta l'imitando il numero di porte occupate e il numero di oggetti
    // istanziati.
    public void sendPacket(DatagramPacket packet){
        try {
            socket.send(packet);
        } catch (IOException ex) {}
    }
    
    public static void main(String[] args) throws Exception {
        // Avvio del server
        new Server().start();
    }
}
