package org.kulejp.house;

import javax.swing.*;
import java.awt.*;

public class HousePanel extends JFrame {
    private JTextField volumeField;
    private JTextField officeHostField;
    private JTextField officePortField;
    private JTextArea logArea;
    private House house;

    public HousePanel(House house) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            // handle exception
        }

        this.house = house;
        setTitle("House Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 5, 5));

        // Volume input and button
        JPanel volumePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        volumeField = new JTextField(10);
        JButton addVolumeButton = new JButton("Add Volume");
        volumePanel.add(new JLabel("Add Volume:"));
        volumePanel.add(volumeField);
        volumePanel.add(addVolumeButton);

        // Office host and port input and button
        JPanel officePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        officeHostField = new JTextField(10);
        officePortField = new JTextField(5);
        JButton sendOrderButton = new JButton("Send Order");
        officePanel.add(new JLabel("Office Host:"));
        officePanel.add(officeHostField);
        officePanel.add(new JLabel("Port:"));
        officePanel.add(officePortField);
        officePanel.add(sendOrderButton);

        topPanel.add(volumePanel);
        topPanel.add(officePanel);
        add(topPanel, BorderLayout.NORTH);

        // Button actions
        addVolumeButton.addActionListener(e -> {
            try {
                int volume = Integer.parseInt(volumeField.getText());
                house.currentVolume += volume;
                logArea.append("Added volume: " + volume+"\n");
                System.out.println("HOUSE: added volume: " + volume );
            } catch (NumberFormatException ex) {
                logArea.append("Invalid volume input\n");
            }
        });

        sendOrderButton.addActionListener(e -> {
            String officeHost = officeHostField.getText();
            String officePort = officePortField.getText();
            if (!officeHost.isEmpty() && !officePort.isEmpty()) {
                house.sendRequestORDER(officeHost, officePort);
                logArea.append("Sent order request to " + officeHost + ":" + officePort + "\n");
            } else {
                logArea.append("Office host or port is empty\n");
            }
        });
    }


    public static void main(String[] args) {
        House house = new House("localhost", 5000, 60);
        HousePanel panel = new HousePanel(house);
        house.startServer();
        panel.setVisible(true);
    }
}