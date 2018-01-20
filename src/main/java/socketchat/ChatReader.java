package socketchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Piia Hartikka
 */
public class ChatReader implements Runnable {

    private final ChatConsole console;
    private final BufferedReader inFromClient;

    public ChatReader(ChatConsole console, BufferedReader inFromClient) {
        this.console = console;
        this.inFromClient = inFromClient;
    }

    @Override
    public void run() {
        while (true) {
            String message;
            try {
                message = inFromClient.readLine();
                if (message == null) {
                    // todo: quit only when multiple null messages arrive
                    console.write("Friend disconnected... quitting!");
                    break;
                }
                console.write("FRIEND: " + message);
            } catch (IOException ex) {
                // todo: handle exception (quit program or recover properly)
                Logger.getLogger(ChatReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
