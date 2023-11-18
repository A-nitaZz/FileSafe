import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;
    

    // Build the user interface
    public FlashCardBuilder() {
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PANEL
        JPanel mainPanel = new JPanel();

        // FONT
        Font font = new Font("Arial", Font.BOLD, 21);

        // QUESTION
        question = new JTextArea(5, 16);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);
        JScrollPane questionScroll = new JScrollPane(question);
        questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // ANSWER
        answer = new JTextArea(5, 16);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(font);
        JScrollPane answerScroll = new JScrollPane(answer);
        answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList<FlashCard>();

        // LABELS
        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");

        // MAIN PANEL COMPONENTS
        mainPanel.add(questionLabel);
        mainPanel.add(questionScroll);
        mainPanel.add(answerLabel);
        mainPanel.add(answerScroll);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        // MENU BAR
        JMenuBar MenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem backMenuItem = new JMenuItem("Back");


        MenuBar.add(fileMenu);
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(backMenuItem);
        

        // ADD EVENTLISTENERS
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        backMenuItem.addActionListener(new BackMenuItemListener());

        frame.setJMenuBar(MenuBar);

        // ADD TO THE FRAME
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(360, 480);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // create a flash card
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }

    }

    class NewMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("New Menu Clicked");
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

    }

    class SaveMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);

            // FILE CHOOSER
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }

        

    }

    class BackMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new FlashcardSelection().setVisible(true);
        }


        

    }
    
    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
    private void saveFile(File selectedFile) {
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
            
            Iterator<FlashCard> cardIterator = cardList.iterator();

            while (cardIterator.hasNext()) {
                FlashCard card = (FlashCard)cardIterator.next();
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
            
            
        } catch (Exception e) {
            System.out.println("Couldn't write to file");
            e.printStackTrace();

        }
        }
}
