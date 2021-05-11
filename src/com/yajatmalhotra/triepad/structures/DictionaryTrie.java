package com.yajatmalhotra.triepad.structures;

import com.yajatmalhotra.triepad.models.DictionaryNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

// Made By: Yajat Malhotra
public class DictionaryTrie {
    private final DictionaryNode root;
    private final int SIZE;

    // Constructor to initialise the values
    public DictionaryTrie() {
        this.root = new DictionaryNode(' ', 26);
        this.SIZE = 26;
    }

    // Function which adds words, meanings and examples to Trie
    public void insert(String word, String meaning, String ex1, String ex2) {
        try {
            DictionaryNode current = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (current.alphabets[ch - 'a'] == null)
                    current.alphabets[ch - 'a'] = new DictionaryNode(ch, SIZE);
                current = current.alphabets[ch - 'a'];
            }
            current.meaning = meaning;
            current.ex1 = ex1;
            current.ex2 = ex2;
        } catch (Exception e) {
            System.out.println(word);
        }
    }

    // Function to search for words
    public DictionaryNode search(String word) {
        DictionaryNode searchDictionaryNode = getNode(word);
        if (searchDictionaryNode != null && searchDictionaryNode.meaning != null) {
            return searchDictionaryNode;
        }
        return null;
    }

    // Function to find the meaning and examples of a given word
    public String define(String word) {
        DictionaryNode dn = search(word);
        if (dn != null) {
            if (dn.ex2.compareTo("") != 0) {
                return "Meaning: " + dn.meaning + "\nExamples:\n1. " + dn.ex1 + "\n2. " + dn.ex2;
            } else {
                return "Meaning: " + dn.meaning + "\nExamples:\n1. " + dn.ex1;
            }
        }
        return "";
    }

    // Function to get a node
    private DictionaryNode getNode(String word) {
        DictionaryNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (current.alphabets[ch - 'a'] == null)
                return null;
            current = current.alphabets[ch - 'a'];
        }
        return current;
    }

    // Loads the dataset in the Trie data structure
    public void loadTrie() throws IOException {
        File file = new File("src/datasets/dict_words.txt");
        Scanner sc = new Scanner(file, StandardCharsets.UTF_8);
        while (sc.hasNextLine()) {
            insert(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
        }
    }

    // Main function to test the code
    public static void main(String[] args) throws IOException {
        DictionaryTrie dt = new DictionaryTrie();
        dt.loadTrie();
        Scanner r = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter a word to search: ");
            String x = r.nextLine().trim();
            System.out.println();
            if (x.compareTo("exit") == 0)
                break;
        }
    }
}
