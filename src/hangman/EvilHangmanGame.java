package hangman;

import dictionary.Dictionary;
import dictionary.WordGroup;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class EvilHangmanGame implements IEvilHangmanGame{
    private final Dictionary dictionary;
    private boolean[] guessedLetters;

    public EvilHangmanGame() {
        this.dictionary = new Dictionary();
        this.guessedLetters = new boolean[26];
        for (int i = 0; i < 26; i++) {
            this.guessedLetters[i] = false;
        }
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        this.dictionary.readDictionary(dictionary, wordLength);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        // if the guess is already made, throw an exception
        if (guessedLetters[guess - 'a']) {
            throw new GuessAlreadyMadeException();
        } else {
            guessedLetters[guess - 'a'] = true;
            WordGroup wordGroup = this.dictionary.tryLetter(guess);
            return new HashSet<>(wordGroup.getWords());
        }
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }
}
