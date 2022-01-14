package dictionary;

import java.util.Vector;

public class WordGroup {
    private final Vector<String> words;
    private boolean[] configuration;

    public WordGroup(Word word) {
        String wordString = word.getWord();
        this.words = new Vector<>();
        this.configuration = new boolean[wordString.length()];
        this.words.add(wordString);
        this.setConfiguration(word);
    }

    private void setConfiguration(Word word) {
        configuration = word.getWordConfiguration();
    }

    public boolean inGroup(Word word) {
        boolean[] wordConfig = word.getWordConfiguration();
        for (int i = 0; i < wordConfig.length; i++) {
            if (wordConfig[i] != configuration[i]) {
                return false;
            }
        }
        words.add(word.getWord());
        return true;
    }

    public int size() {
        return words.size();
    }

    public Vector<String> getWords() {
        return words;
    }
}
