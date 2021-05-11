package com.yajatmalhotra.triepad.structures;

import com.yajatmalhotra.triepad.models.AutoCompleteNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

// Made By: Yajat Malhotra
public class AutoCompleteTrie {
    private final AutoCompleteNode root;
    private final int SIZE;

    // Constructor to initialise the values
    public AutoCompleteTrie() {
        this.root = new AutoCompleteNode(' ');
        this.SIZE = 26;
    }

    // Function which adds words to Trie
    public void insert(String word) {
        try {
            AutoCompleteNode current = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (current.alphabets[ch - 'a'] == null)
                    current.alphabets[ch - 'a'] = new AutoCompleteNode(ch);
                current = current.alphabets[ch - 'a'];
            }
            current.isWord = true;
        } catch (Exception e) {
            System.out.println(word);
        }
    }

    // Function to search for words
    public boolean search(String word) {
        AutoCompleteNode searchNode = getNode(word);
        return searchNode != null && searchNode.isWord;
    }

    // Function which checks if a word which starts with some prefix is available in the Trie data structure or not
    public boolean startsWith(String prefix) {
        return getNode(prefix) != null;
    }

    // Function which finds the last node of words (i.e, where the word ends)
    public boolean lastNode(AutoCompleteNode n) {
        for (int i = 0; i < SIZE; i++)
            if (n.alphabets[i] != null)
                return false;
        return true;
    }

    // Function which gives 'amt' number of suggestions to complete a word
    public String[] autoComplete(String prefix, int amt) {
        AutoCompleteNode n = getNode(prefix);
        String[] words = new String[amt];
        if (n != null) {
            words = suggestions(n, prefix, amt, words);
        }
        return words;
    }

    // Function which processes finds the words in the Trie data structure
    public String[] suggestions(AutoCompleteNode n, String prefix, int amt, String[] arr) {
        if(amt > 0) {
            if (n.isWord) {
                arr[amt-1] = prefix;
                amt--;
            }

            if (lastNode(n)) {
                return arr;
            }

            for (int i = 0; i < SIZE; i++) {
                if (n.alphabets[i] != null) {
                    prefix += (char) (97 + i);
                    suggestions(n.alphabets[i], prefix, amt, arr);
                    prefix = prefix.substring(0, prefix.length() - 1);
                }
            }
        }
        return arr;
    }

    // Function to get a node
    private AutoCompleteNode getNode(String word) {
        AutoCompleteNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (current.alphabets[ch - 'a'] == null)
                return null;
            current = current.alphabets[ch - 'a'];
        }
        return current;
    }

    // Loads the dataset in the Trie data structure
    public void loadTrie() throws IOException{
        File file = new File("src/datasets/auto_words.txt");
        Scanner sc = new Scanner(file, StandardCharsets.UTF_8);
        while (sc.hasNextLine()) {
            insert(sc.nextLine());
        }
    }

    // Main function to test the code
    public static void main(String[] args) throws IOException {
        AutoCompleteTrie act = new AutoCompleteTrie();
        act.loadTrie();
        Scanner r = new Scanner(System.in);
        System.out.print("\nEnter prefix to complete: ");
        String x = r.nextLine().trim();
        act.autoComplete(x, 3);
    }
}
