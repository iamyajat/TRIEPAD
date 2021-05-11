package com.yajatmalhotra.triepad.models;

public class AutoCompleteNode {
    public char ch;
    public boolean isWord;
    public AutoCompleteNode[] alphabets;

    public AutoCompleteNode(char ch) {
        this.ch = ch;
        this.isWord = false;
        this.alphabets = new AutoCompleteNode[26];
    }
}
