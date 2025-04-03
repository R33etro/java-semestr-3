package org.kulejp.sewagePlant;

import javax.swing.*;
import java.awt.*;

public class SewagePlantPanel extends JFrame {
    private JTextArea textArea;
    private SewagePlant sewagePlant;

    public SewagePlantPanel(SewagePlant sewagePlant) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            // handle exception
        }

        this.sewagePlant = sewagePlant;
        setTitle("Sewage Plant Panel");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SewagePlant sewagePlant = new SewagePlant("localhost", 7000);
        SewagePlantPanel panel = new SewagePlantPanel(sewagePlant);
        sewagePlant.startServer();
        panel.setVisible(true);
    }
}
