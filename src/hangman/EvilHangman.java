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

        int guessCount = 0;

        while (guessCount < guessCountMax) {
            System.out.printf("You have %d guesses left\n", guessCountMax - guessCount);

            StringBuilder lettersUsed = new StringBuilder();
            for (char c : game.getGuessedLetters()) {
                lettersUsed.append(c).append(" ");
            }
            System.out.println("Used letters: " + lettersUsed);

            StringBuilder completedWordSoFar = new StringBuilder();
            for (char c : game.getCompletedWordSoFar()) {
                completedWordSoFar.append(c);
            }
            System.out.println("Word: " + completedWordSoFar);

            System.out.print("Enter guess: ");
            char guess = GuessInput.getGuess();
            try {
                game.makeGuess(guess);
                if (game.getNumberOfCorrectSpacesOnLastGuess() > 0) {
                    System.out.println("Yes, there is " + game.getNumberOfCorrectSpacesOnLastGuess() + " " + guess);
                } else {
                    System.out.println("Sorry, there are no " + guess + "'s");
                    guessCount++;
                }
                if (game.isGameOver()) {
                    System.out.println("You win!");
                    System.out.println("The word was: " + game.getWord());
                    return;
                } else if (guessCount >= guessCountMax) {
                    System.out.println("You lose!");
                    System.out.println("The word was: " + game.getWord());
                    return;
                }
            } catch (GuessAlreadyMadeException e) {
                System.out.println("You already guessed that letter");
            }
            System.out.println();
        }
    }

}
