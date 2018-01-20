package socketchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Piia Hartikka
 */
public class ChatConnector {

    private final int serverPort;
    private final ChatConsole console;
    private ServerSocket serverSocket;
    private Socket connection;

    private final Thread caller = new Thread(new Runnable() {
        @Override
        public void run() {

            String clientAddress;
            int clientPort;

            try {
                console.write("Give friend's IP-address: ");
                clientAddress = console.readNext();

                console.write("Give friend's port number: ");
                // todo: prevent NumberFormatException
                clientPort = Integer.parseInt(console.readNext());

                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatConnector.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    connection = new Socket(clientAddress, clientPort);
                } catch (IOException ex) {
                    // todo: handle exception (quit program or recover properly)
                    Logger.getLogger(ChatConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InterruptedException e) {
                // Callee connected and stops this thread with an interrupt.
            }
        }
    });

    private final Thread callee = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                connection = serverSocket.accept();
                caller.interrupt(); // someone called us => stop caller thread
            } catch (SocketException e) {
                // caller thread closed serverSocket which causes SocketException
            } catch (IOException ex) {
                // todo: handle exception (quit program or recover properly)
                Logger.getLogger(ChatConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

    ChatConnector(int serverPort, ChatConsole console) throws IOException {
        this.serverPort = serverPort;
        this.console = console;
        this.serverSocket = new ServerSocket(this.serverPort);
    }

    ChatConnector(ChatConsole console) throws IOException {
        this.serverPort = 8888; // todo: ask user
        this.console = console;
        this.serverSocket = new ServerSocket(this.serverPort);
    }

    public Socket connect() throws InterruptedException {
        if (connection != null) {
            return connection;
        }

        callee.setDaemon(true);
        caller.setDaemon(true);
        callee.start();
        caller.start();
        callee.join();
        caller.join();

        return connection;
    }

}
