package dictionary;

public class Word {
    private boolean[] wordConfiguration;
    private String word;

    public Word(String word, char letter) {
        wordConfiguration = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordConfiguration[i] = word.charAt(i) == letter;
        }
        this.word = word;
    }

    public boolean[] getWordConfiguration() {
        return wordConfiguration;
    }

    public String getWord() {
        return word;
    }
}
