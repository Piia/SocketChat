package socketchat;

import java.util.Scanner;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Piia Hartikka
 */
public class ChatConsole implements Runnable {

    private final Scanner scanner;
    private final LinkedBlockingQueue<String> queue;

    public ChatConsole() {
        this.scanner = new Scanner(System.in);
        this.queue = new LinkedBlockingQueue<>();
    }

    public void write(String message) {
        System.out.println(message);
    }

    public String readNext() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void run() {
        while(true) {
            try {
                queue.put(scanner.nextLine());
            } catch (InterruptedException ex) {
                // todo: handle exception (quit program or recover properly)
                Logger.getLogger(ChatConsole.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
