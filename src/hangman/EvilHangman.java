package hangman;

import java.io.File;
import java.io.IOException;

public class EvilHangman {

    public static void main(String[] args) throws EmptyDictionaryException, IOException {
        File dictionary = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);

        EvilHangmanGame game = new EvilHangmanGame();
        game.startGame(dictionary, wordLength);

    }

}
