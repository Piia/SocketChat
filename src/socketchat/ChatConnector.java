/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private Socket clientSocket;
        
    private final Thread caller = new Thread(new Runnable() {
        @Override
        public void run() {
            
            String clientAddress;
            int clientPort;
            
            try {
                console.write("Give address: ");                
                clientAddress = console.pollNext();

                console.write("Give port number: ");
                clientPort = Integer.parseInt(console.pollNext());

                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatConnector.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    clientSocket = new Socket(clientAddress, clientPort);
                } catch (IOException ex) {
                    Logger.getLogger(ChatConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch(InterruptedException e) {
                // Interrupt is used to stop this thread.
            }
        }
    });
    
    private final Thread callee = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                clientSocket = serverSocket.accept();
                caller.interrupt();
            } catch (SocketException e){
                
            } catch (IOException ex) {
                Logger.getLogger(ChatConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
    
    ChatConnector(int serverPort, ChatConsole console) throws IOException {
        this.serverPort = serverPort;
        this.console = console;
        this.serverSocket = new ServerSocket(this.serverPort);
    }
    
    public Socket getConnection() throws InterruptedException {
        if(clientSocket != null) {
            return clientSocket;
        }
        
        callee.start();
        caller.start();
        
        callee.join();
        caller.join();
        
        return clientSocket;
    }
}
