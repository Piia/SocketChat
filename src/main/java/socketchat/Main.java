package socketchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Piia Hartikka
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatConsole console = new ChatConsole();
        Thread consoleThread = new Thread(console);
        consoleThread.setDaemon(true);
        consoleThread.start();

        ChatConnector connector = new ChatConnector(console);
        console.write("Waiting for incoming connection or your input... ");
        Socket connection = connector.connect();
        console.write("... got connection!");

        BufferedReader inFromClient = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        ChatReader reader = new ChatReader(console, inFromClient);

        DataOutputStream outToClient = new DataOutputStream(
                connection.getOutputStream()
        );
        ChatWriter writer = new ChatWriter(console, outToClient);

        Thread readerThread = new Thread(reader);
        Thread writerThread = new Thread(writer);
        readerThread.setDaemon(true);
        writerThread.setDaemon(true);
        readerThread.start();
        writerThread.start();

        readerThread.join();
        console.write("QUIT");
    }

}
