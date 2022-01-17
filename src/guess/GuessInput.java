package guess;

import java.util.Scanner;

public class GuessInput {
    Scanner scanner;

    public GuessInput() {
         scanner = new Scanner(System.in);
    }

    public char getGuess() {
        while (true) {
            String input = scanner.next();
            // check if input is a single character
            if (input.length() == 1) {
                // check if input is alphabetic a-z and A-Z
                if (input.matches("[a-zA-Z]")) {
                    return input.toLowerCase().charAt(0);
                }
            }
            System.out.printf("Sorry, there are no %s’s", input);
        }

    }
}
