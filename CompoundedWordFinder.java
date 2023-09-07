import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;

    TrieNode() {
        children = new TrieNode[26];
        isEndOfWord = false;
    }
}

public class CompoundedWordFinder {
    public static void main(String[] args) {
        String inputFile = "Input_01.txt";
        String[] words = readWordsFromFile(inputFile);

        long startTime = System.currentTimeMillis();

        String longestCompoundWord = findLongestCompoundedWord(words);
        String secondLongestCompoundWord = findSecondLongestCompoundedWord(words);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Longest Compounded Word: " + longestCompoundWord);
        System.out.println("Second Longest Compounded Word: " + secondLongestCompoundWord);
        System.out.println("Time taken to process the input words: " + elapsedTime + " milliseconds");
    }

    private static String[] readWordsFromFile(String inputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            Set<String> wordSet = new HashSet<>();

            while ((line = br.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    wordSet.add(word);
                }
            }

            return wordSet.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private static String findLongestCompoundedWord(String[] words) {
        Set<String> wordSet = new HashSet<>(Arrays.asList(words));
        Arrays.sort(words, Comparator.comparingInt(String::length).reversed());

        for (String word : words) {
            wordSet.remove(word);
            if (canBeFormed(word, wordSet)) {
                return word;
            }
            wordSet.add(word);
        }

        return "";
    }

    private static String findSecondLongestCompoundedWord(String[] words) {
        Set<String> wordSet = new HashSet<>(Arrays.asList(words));
        Arrays.sort(words, Comparator.comparingInt(String::length).reversed());

        String longestCompoundWord = findLongestCompoundedWord(words);
        for (String word : words) {
            if (!word.equals(longestCompoundWord)) {
                wordSet.remove(word);
                if (canBeFormed(word, wordSet)) {
                    return word;
                }
                wordSet.add(word);
            }
        }

        return "";
    }

    private static boolean canBeFormed(String word, Set<String> wordSet) {
        if (wordSet.contains(word)) {
            return true;
        }

        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);
            if (wordSet.contains(prefix) && canBeFormed(suffix, wordSet)) {
                return true;
            }
        }

        return false;
    }
}
