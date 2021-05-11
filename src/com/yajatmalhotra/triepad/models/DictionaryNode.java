package com.yajatmalhotra.triepad.models;

// Made By: Yajat Malhotra
public class DictionaryNode {
    public char ch;
    public String meaning;
    public String ex1;
    public String ex2;
    public DictionaryNode[] alphabets;

    public DictionaryNode(char ch, int size) {
        this.ch = ch;
        this.meaning = null;
        this.ex1 = null;
        this.ex2 = null;
        this.alphabets = new DictionaryNode[size];
    }
}