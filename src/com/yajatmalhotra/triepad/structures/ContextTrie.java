package com.yajatmalhotra.triepad.structures;

import com.yajatmalhotra.triepad.models.ContextNode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

// Made By: Yajat Malhotra
public class ContextTrie {
    private final ContextNode root;
    private final int SIZE;

    // Constructor to initialise the values
    public ContextTrie() {
        this.root = new ContextNode(' ');
        this.SIZE = 27;
    }

    // Function which adds a phrase to Trie
    public void insert(String word) {
        try {
            ContextNode current = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (ch != ' ') {
                    if (current.alphabets[ch - 'a'] == null)
                        current.alphabets[ch - 'a'] = new ContextNode(ch);
                    current = current.alphabets[ch - 'a'];
                } else {
                    if (current.alphabets[26] == null)
                        current.alphabets[26] = new ContextNode(ch);
                    current = current.alphabets[26];
                }
            }
            current.isPhrase = true;
        } catch (Exception e) {
            System.out.println(word);
        }
    }

    // Function to search for words
    public boolean search(String word) {
        ContextNode searchNode = getNode(word);
        return searchNode != null && searchNode.isPhrase;
    }

    // Function which checks if a word which starts with some prefix is available in the Trie data structure or not
    public boolean startsWith(String prefix) {
        return getNode(prefix) != null;
    }

    // Function which finds the last node of words (i.e, where the word ends)
    public boolean lastNode(ContextNode n) {
        for (int i = 0; i < SIZE; i++)
            if (n.alphabets[i] != null)
                return false;
        return true;
    }

    // Function which gives 'amt' number of suggestions to complete a word
    public String[] autoComplete(String prefix, int amt) {
        ContextNode n = getNode(prefix);
        String[] words = new String[amt];
        if (n != null) {
            words = suggestions(n, prefix, amt, words);
        }
        return words;
    }

    // Function which processes finds the words in the Trie data structure
    public String[] suggestions(ContextNode n, String prefix, int amt, String[] arr) {
        if (amt > 0) {
            if (n.isPhrase) {
                arr[amt - 1] = prefix;
                amt--;
            }

            if (lastNode(n)) {
                return arr;
            }

            for (int i = 0; i < SIZE; i++) {
                if (n.alphabets[i] != null) {
                    if (i!=26) {
                        prefix += (char) (97 + i);
                    } else {
                        prefix += " ";
                    }
                    suggestions(n.alphabets[i], prefix, amt, arr);
                    prefix = prefix.substring(0, prefix.length() - 1);
                }
            }
        }
        return arr;
    }

    // Function to get a node
    private ContextNode getNode(String word) {
        ContextNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch != ' ') {
                if (current.alphabets[ch - 'a'] == null)
                    return null;
                current = current.alphabets[ch - 'a'];
            } else {
                if (current.alphabets[26] == null)
                    return null;
                current = current.alphabets[26];
            }
        }
        return current;
    }

    // Loads the dataset in the Trie data structure
    public void loadTrie() throws IOException {
        File file = new File("src/datasets/context_words.txt");
        Scanner sc = new Scanner(file, StandardCharsets.UTF_8);
        while (sc.hasNextLine()) {
            insert(sc.nextLine());
        }
    }

    // Main function to test the code
    public static void main(String[] args) throws IOException {
        ContextTrie act = new ContextTrie();
        act.loadTrie();
        Scanner r = new Scanner(System.in);
        System.out.print("\nEnter prefix to complete: ");
        String x = r.nextLine().trim();
        String[] y = act.autoComplete(x, 3);
        for (String s : y) {
            System.out.println(s + "\n");
        }
    }
}
