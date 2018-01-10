/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        while(true) {
            try {
                console.write("FRIEND: " + inFromClient.readLine());
            } catch (IOException ex) {
                Logger.getLogger(ChatReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
