package com.yajatmalhotra.triepad.ui;

import com.yajatmalhotra.triepad.structures.AutoCompleteTrie;
import com.yajatmalhotra.triepad.structures.ContextTrie;
import com.yajatmalhotra.triepad.structures.DictionaryTrie;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;

// Made By: Yajat Malhotra
public class Notepad {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextArea textArea1;
    private JPanel panel;
    JFrame frame = new JFrame("TRIEPAD");
    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem openFile = new JMenuItem("Open");
    JMenuItem saveFile = new JMenuItem("Save");
    JMenuItem close = new JMenuItem("Close");
    JMenu tools = new JMenu("Tools");
    JMenuItem dictTool = new JMenuItem("Dictionary");

    private static final AutoCompleteTrie act = new AutoCompleteTrie();
    private static final ContextTrie ct = new ContextTrie();
    private static final DictionaryTrie dt = new DictionaryTrie();

    public Notepad() {
        button1.setFocusable(false);
        button2.setFocusable(false);
        button3.setFocusable(false);

        button1.addActionListener(e -> {
            updateTextArea(button1.getText());
        });
        button2.addActionListener(e -> {
            updateTextArea(button2.getText());
        });
        button3.addActionListener(e -> {
            updateTextArea(button3.getText());
        });
        textArea1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtons();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtons();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtons();
            }
        });
        openFile.addActionListener(e -> {
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    FileReader fr = new FileReader(open.getSelectedFile().getPath());
                    BufferedReader br = new BufferedReader(fr);
                    textArea1.read(br, null);
                    br.close();
                    textArea1.requestFocus();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        saveFile.addActionListener(e -> {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(textArea1.getText());
                    out.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        close.addActionListener(e -> {
            System.exit(0);
        });

        dictTool.addActionListener(e -> {
            String word = JOptionPane.showInputDialog(frame, "Enter a word", "Dictionary", JOptionPane.QUESTION_MESSAGE);
            String meaning = dt.define(word.toLowerCase());
            try {
                showMeaning(word, meaning);
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        });
    }

    void showMeaning(String word, String meaning) throws MalformedURLException {
        ImageIcon icon = new ImageIcon("src/assets/dictionary.png");
        if (meaning.compareTo("") != 0)
            JOptionPane.showMessageDialog(frame, meaning, "Meaning: " + word, JOptionPane.INFORMATION_MESSAGE, icon);
        else
            JOptionPane.showMessageDialog(frame, "The word you're searching for is not there in the dictionary.", "WORD NOT FOUND!", JOptionPane.ERROR_MESSAGE);
    }

    void updateTextArea(String opt) {
        String text = textArea1.getText();
        String[] y = text.split(" ", -1);
        StringBuilder fin = new StringBuilder();
        for (int i = 0; i < y.length - 1; i++) {
            fin.append(y[i]);
            fin.append(" ");
        }
        fin.append(opt);
        fin.append(" ");
        textArea1.setText(fin.toString());
    }

    void updateButtons() {
        String text = textArea1.getText().toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch < 'a' || ch > 'z') {
                text = text.replace(ch, ' ');
            }
        }
        String[] y = text.split(" ", -1);

        String[] multiWords = new String[3];
        String[] words = act.autoComplete(y[y.length - 1], 3);
        ;
        if (y.length >= 2)
            multiWords = ct.autoComplete(y[y.length - 2] + " " + y[y.length - 1], 3);

        for (int i = 0; i < 3; i++) {
            if (multiWords[i] != null && multiWords[i].compareTo("") != 0) {
                String[] z = multiWords[i].split(" ", -1);
                words[i] = z[z.length - 1];
            }
        }

        if (words[2] != null) {
            button1.setText(words[2]);
            button1.setEnabled(true);
        } else {
            button1.setText("No Suggestion");
            button1.setEnabled(false);
        }
        if (words[1] != null) {
            button2.setText(words[1]);
            button2.setEnabled(true);
        } else {
            button2.setText("No Suggestion");
            button2.setEnabled(false);
        }
        if (words[0] != null) {
            button3.setText(words[0]);
            button3.setEnabled(true);
        } else {
            button3.setText("No Suggestion");
            button3.setEnabled(false);
        }
    }

    void setupUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        file.add(openFile);
        file.add(saveFile);
        file.add(close);
        menuBar.add(file);
        tools.add(dictTool);
        menuBar.add(tools);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(new Notepad().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        act.loadTrie();
        ct.loadTrie();
        dt.loadTrie();
        Notepad notepad = new Notepad();
        notepad.setupUI();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(5, 5, 5, 5), -1, -1));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        button1 = new JButton();
        button1.setText("you");
        panel.add(button1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button2 = new JButton();
        button2.setText("the");
        panel.add(button2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button3 = new JButton();
        button3.setText("a");
        panel.add(button3, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        textArea1.setLineWrap(true);
        panel.add(textArea1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(500, 400), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
