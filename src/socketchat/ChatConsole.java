package socketchat;

import java.util.Scanner;

/**
 *
 * @author Piia Hartikka
 */
public class ChatConsole {

    private final Scanner scanner;

    public ChatConsole() {
        this.scanner = new Scanner(System.in);
    }

    public void write(String message) {
        System.out.println(message);
    }

    public String readNext() {
        return scanner.nextLine();
    }

    public String pollNext() throws InterruptedException {
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
    }

    private boolean hasNextLine() {
        return scanner.hasNextLine();
    }

}
