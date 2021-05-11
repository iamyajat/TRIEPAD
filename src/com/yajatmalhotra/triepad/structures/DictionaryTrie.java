package com.yajatmalhotra.triepad.structures;

import com.yajatmalhotra.triepad.models.DictionaryNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DictionaryTrie {
    private final DictionaryNode root;
    private final int SIZE;

    public DictionaryTrie() {
        this.root = new DictionaryNode(' ', 26);
        this.SIZE = 26;
    }

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

    public DictionaryNode search(String word) {
        DictionaryNode searchDictionaryNode = getNode(word);
        if (searchDictionaryNode != null && searchDictionaryNode.meaning != null) {
            return searchDictionaryNode;
        }
        return null;
    }

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

    public void loadTrie() throws IOException {
        File file = new File("./wordsets/dict_words.txt");
        Scanner sc = new Scanner(file, StandardCharsets.UTF_8);
        while (sc.hasNextLine()) {
            insert(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
        }
    }


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