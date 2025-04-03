package org.kulejp.office;

import javax.swing.*;
import java.awt.*;

public class OfficePanel extends JFrame{
//    private JFrame frame;

    private JTextField tankerNumberField;
    private JTextArea logArea;
    private Office office;

    public OfficePanel(Office office) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            // handle exception
        }

        this.office = office;
        setTitle("Office Panel");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JPanel tankerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tankerNumberField = new JTextField(10);
        JButton setPayoffButton = new JButton("Set payoff");
        JButton getStatusButton = new JButton("Get status");
        tankerPanel.add(new JLabel("Tanker Number:"));
        tankerPanel.add(tankerNumberField);
        tankerPanel.add(getStatusButton);
        tankerPanel.add(setPayoffButton);

        topPanel.add(tankerPanel);
        add(topPanel, BorderLayout.NORTH);


        getStatusButton.addActionListener(e -> {
            String tankerNumber = tankerNumberField.getText();
            office.sendRequestGETSTATUS(tankerNumber);
            logArea.append("Get Status request sent for tanker " + tankerNumber + "\n");
        });

        setPayoffButton.addActionListener(e -> {
            String tankerNumber = tankerNumberField.getText();
            office.sendRequestSETPAYOFF(tankerNumber);
            logArea.append("Set Payoff request sent for tanker " + tankerNumber + "\n");
        });

    }

    public static void main(String[] args) {
        Office office = new Office("localhost", 6000, "localhost", 7000);
        OfficePanel panel = new OfficePanel(office);
        office.startServer();
        panel.setVisible(true);
    }
}
