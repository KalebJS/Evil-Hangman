package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private final SortedSet<Character> guessedLetters;
    private Set<String> words;
    private char[] completedWordSoFar;
    private int numberOfCorrectSpacesOnLastGuess;


    public EvilHangmanGame() {
        words = new HashSet<>();
        guessedLetters = new TreeSet<>();
        numberOfCorrectSpacesOnLastGuess = 0;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Scanner scanner = new Scanner(dictionary);
        words.clear();
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            word = word.replaceAll("[^a-zA-Z]", "");
            if (word.length() == wordLength) {
                words.add(word);
            }
        }
        if (words.size() == 0) {
            throw new EmptyDictionaryException();
        }

        completedWordSoFar = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            completedWordSoFar[i] = '-';
        }
    }

    public char[] getCompletedWordSoFar() {
        return completedWordSoFar;
    }

    private static String getPartitionId(String word, char letter) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (c == letter) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    private static int getNumberOfTrue(String id) {
        int count = 0;
        for (char c : id.toCharArray()) {
            if (c == '1') {
                count++;
            }
        }
        return count;
    }

    private static String determinePriorityPartition(String id1, String id2) {
        int charCount1 = getNumberOfTrue(id1);
        int charCount2 = getNumberOfTrue(id2);
        // whichever has fewer characters in it is the priority partition
        if (charCount1 < charCount2) {
            return id1;
        } else if (charCount1 > charCount2) {
            return id2;
            // if equal partitions, go by whichever has the first rightmost letter
        } else {
            for (int j = 0; j < id1.length(); j++) {
                boolean id1HasRightmostLetter = id1.charAt(id1.length() - j - 1) == '1';
                boolean id2HasRightmostLetter = id2.charAt(id2.length() - j - 1) == '1';
                if (id1HasRightmostLetter && !id2HasRightmostLetter) {
                    return id1;
                } else if (!id1HasRightmostLetter && id2HasRightmostLetter) {
                    return id2;
                }
            }
            // it should be impossible to get here. They should never be equal.
            throw new IllegalArgumentException("id1 and id2 are equal");
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        // convert guess to lowercase
        guess = Character.toLowerCase(guess);
        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException();
        } else {
            numberOfCorrectSpacesOnLastGuess = 0;
            guessedLetters.add(guess);
            Map<String, Set<String>> partitions = new HashMap<>();
            // add each word into a partition depending on its letter id (boolean[])
            for (String word : words) {
                String key = getPartitionId(word, guess);
                if (partitions.containsKey(key)) {
                    partitions.get(key).add(word);
                } else {
                    partitions.put(key, new HashSet<>(List.of(word)));
                }
            }

            // determine the largest partition
            Set<String> largestPartition = new HashSet<>();
            String largestPartitionKey = "";
            for (String key : partitions.keySet()) {
                // in the case that this partition is just outright larger than the previous
                if (partitions.get(key).size() > largestPartition.size()) {
                    largestPartition = partitions.get(key);
                    largestPartitionKey = key;
                    // in the case that the partitions are equal we'll need a tiebreaker
                } else if (partitions.get(key).size() == largestPartition.size()) {
                    largestPartitionKey = determinePriorityPartition(largestPartitionKey, key);
                    largestPartition = partitions.get(largestPartitionKey);

                }
            }
            numberOfCorrectSpacesOnLastGuess = getNumberOfTrue(largestPartitionKey);
            for (int i = 0; i < largestPartitionKey.length(); i++) {
                if (largestPartitionKey.charAt(i) == '1') {
                    completedWordSoFar[i] = guess;
                }
            }
            words = largestPartition;
            return largestPartition;
        }
    }

    public boolean isGameOver() {
        for (char c : completedWordSoFar) {
            if (c == '-') {
                return false;
            }
        }
        return true;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String getWord() {
        // get random word from the set of words
        String[] arrayNumbers = words.toArray(new String[0]);
        Random random = new Random();
        int randomIndex = random.nextInt(arrayNumbers.length);
        return arrayNumbers[randomIndex];
    }

    public int getNumberOfCorrectSpacesOnLastGuess() {
        return numberOfCorrectSpacesOnLastGuess;
    }
}














