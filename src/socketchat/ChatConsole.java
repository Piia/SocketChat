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
    
}
