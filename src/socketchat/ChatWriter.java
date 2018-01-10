/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        while(true) {
            String message;
            try {
                message = console.readNext() + "\n";
            } catch (InterruptedException ex) {
                Logger.getLogger(ChatWriter.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalStateException("console was interrupted while writing");
            }
            
            try {
                outToClient.writeBytes(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            console.write("YOU: " + message);
    
        }
    }
}
