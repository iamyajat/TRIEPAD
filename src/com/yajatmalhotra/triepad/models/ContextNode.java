package com.yajatmalhotra.triepad.models;

// Made By: Yajat Malhotra
public class ContextNode {
    public char ch;
    public boolean isPhrase;
    public ContextNode[] alphabets;

    public ContextNode(char ch) {
        this.ch = ch;
        this.isPhrase = false;
        this.alphabets = new ContextNode[27];
    }
}
