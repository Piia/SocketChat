package socketchat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Piia Hartikka
 */
public class ChatWriter implements Runnable {

    private final ChatConsole console;
    private final DataOutputStream outToClient;

    ChatWriter(ChatConsole console, DataOutputStream outToClient) {
        this.console = console;
        this.outToClient = outToClient;
    }

    @Override
    public void run() {
        while (true) {
            String message;
            try {
                message = console.readNext() + "\n";
            } catch (InterruptedException ex) {
                // todo: handle exception (quit program or recover properly)
                Logger.getLogger(ChatWriter.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalStateException("Console was interrupted while reading user input");
            }

            try {
                outToClient.writeBytes(message);
            } catch (IOException ex) {
                // todo: handle exception (quit program or recover properly)
                Logger.getLogger(ChatWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            console.write("YOU: " + message);
        }
    }
}
