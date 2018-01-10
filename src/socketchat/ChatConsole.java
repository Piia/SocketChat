package socketchat;

import java.util.Scanner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Piia Hartikka
 */
public class ChatConsole implements Runnable {

    private final Scanner scanner;
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public ChatConsole() {
        this.scanner = new Scanner(System.in);
    }

    public void write(String message) {
        System.out.println(message);
    }

    public String readNext() throws InterruptedException {
        //return scanner.nextLine();
        return queue.take();
    }

    /*public String pollNext() throws InterruptedException {
        String nextInput;
        while (true) {
            if (this.hasNextLine()) {
                nextInput = this.readNext();
                break;
            } else {
                Thread.sleep(60);
            }
        }
        return nextInput;
    }*/
        

    //private boolean hasNextLine() {
    //    return scanner.hasNextLine();
    //}

    @Override
    public void run() {
        while(true) {
            try {
                queue.put(scanner.nextLine() + "\n");
            } catch (InterruptedException ex) {
                Logger.getLogger(ChatConsole.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
