package hangman;

import guess.GuessInput;

import java.io.File;
import java.io.IOException;

public class EvilHangman {

    public static void main(String[] args) throws EmptyDictionaryException, IOException, GuessAlreadyMadeException {
        File dictionary = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        int guessCountMax = Integer.parseInt(args[2]);

        EvilHangmanGame game = new EvilHangmanGame();
        game.startGame(dictionary, wordLength);

        GuessInput input = new GuessInput();

        int guessCount = 0;

        while (guessCount < guessCountMax) {
            char guess = input.getGuess();
//            game.makeGuess(guess);

        }
    }

}
