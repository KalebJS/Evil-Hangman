package dictionary;

import hangman.EmptyDictionaryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Dictionary {
    private final Vector<String> words;

    public Dictionary() {
        words = new Vector<>();
    }

    public void readDictionary(File dictionary, int wordLength) throws FileNotFoundException, EmptyDictionaryException {
        Scanner scanner = new Scanner(dictionary);

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
    }

    private Vector<WordGroup> generateWordGroups(char letter) {
        Vector<WordGroup> wordGroups = new Vector<>();
        for (String word : words) {
            Word w = new Word(word, letter);
            boolean hasGroup = false;
            for (WordGroup wordGroup : wordGroups) {
                if (wordGroup.inGroup(w)) {
                    hasGroup = true;
                }
            }
            if (!hasGroup) {
                WordGroup wordGroup = new WordGroup(w);
                wordGroups.add(wordGroup);
            }
        }
        return wordGroups;
    }

    private WordGroup generateLargestWordGroup(Vector<WordGroup> wordGroups) {
        WordGroup largestGroup = wordGroups.get(0);
        Vector<WordGroup> largestGroupsOfEqualSize = new Vector<>();
        for (WordGroup wordGroup : wordGroups) {
            if (wordGroup.size() > largestGroup.size()) {
                largestGroup = wordGroup;
                largestGroupsOfEqualSize.clear();
            } else if (wordGroup.size() == largestGroup.size()) {
                largestGroupsOfEqualSize.add(wordGroup);
            }
        }
        if (largestGroupsOfEqualSize.size() > 0) {

        }
        return largestGroup;
    }

    public WordGroup tryLetter(char letter) {
        Vector<WordGroup> wordGroups = generateWordGroups(letter);
        WordGroup largestGroup = generateLargestWordGroup(wordGroups);
        return largestGroup;
    }

}
