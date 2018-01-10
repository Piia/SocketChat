package socketchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Piia
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatConsole console = new ChatConsole();
        ChatConnector connector = new ChatConnector(8888, console);
        console.write("DEBUG: waiting for connection...");
        Socket connection = connector.getConnection();
        console.write("DEBUG: ... got connection");
        BufferedReader inFromClient = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        ChatReader chatReader = new ChatReader(console, inFromClient);
        console.write("DEBUG: got chatReader");
        DataOutputStream outToClient = new DataOutputStream(
                connection.getOutputStream()
        );
        ChatWriter chatWriter = new ChatWriter(console, outToClient);
        console.write("DEBUG: got chatWriter");
        
        Thread readerThread = new Thread(chatReader);
        Thread writerThread = new Thread(chatWriter);
        
        readerThread.start();
        writerThread.start();
        
        readerThread.join();
        writerThread.join();
    }

}
