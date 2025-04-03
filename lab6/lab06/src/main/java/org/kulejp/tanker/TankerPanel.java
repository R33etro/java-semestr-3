package org.kulejp.tanker;

import javax.swing.*;
import java.awt.*;

public class TankerPanel extends JFrame {
    private JFrame frame;
    private JTextField maxVolField;
    private JTextField officeHostField;
    private JTextField officePortField;
    private JTextArea logArea;
    private Tanker tanker;
    private int port = 4000;

    public TankerPanel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            // handle exception
        }

//        this.tanker = tanker;
        setTitle("Tanker Panel");
        setSize(500, 300);
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

        // Office host and port input and button
        JPanel tankerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maxVolField =  new JTextField(5);
        officeHostField = new JTextField(10);
        officePortField = new JTextField(5);

        JButton registerButton = new JButton("Register");

        tankerPanel.add(new JLabel("Max Vol"));
        tankerPanel.add(maxVolField);
        tankerPanel.add(new JLabel("Office Host:"));
        tankerPanel.add(officeHostField);
        tankerPanel.add(new JLabel("Port:"));
        tankerPanel.add(officePortField);

        tankerPanel.add(registerButton);

        topPanel.add(tankerPanel);
        add(topPanel, BorderLayout.NORTH);


        registerButton.addActionListener(e -> {
            try{
                int vol = Integer.parseInt(maxVolField.getText());
                String host = officeHostField.getText();
                String port = officePortField.getText();
                Tanker tanker = new Tanker("localhost", this.port, host, port, "localhost", "7000", vol);
                this.port++;
                tanker.sendRequestREGISTER(host, port);
                tanker.startServer();
                logArea.append("tanker registered" + "\n");
            } catch (NumberFormatException nfe){
                logArea.append(nfe.getMessage()+"\n");
            }
        });

    }

//    private void registerTanker() {
//        try {
//            String host = "localhost";
//            int port = 4000;
//            String officeHost = officeHostField.getText();
//            String officePort = officePortField.getText();
//            String sewagePlantHost = "localhost";
//            String sewagePlantPort = "7000";
//            int maxVol = Integer.parseInt(maxVolField.getText());
//
//            tanker = new Tanker(host, port, officeHost, officePort, sewagePlantHost, sewagePlantPort, maxVol);
//            tanker.startServer();
//            tanker.sendRequestREGISTER();
//            logArea.append("Tanker registered successfully!\n");
//        } catch (Exception ex) {
//            logArea.append("Error: " + ex.getMessage() + "\n");
//        }
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TankerPanel::new);
//        Tanker tanker = new Tanker("localhost", 4000, "localhost", "6000", "localhost", "7000", 30);
        TankerPanel panel = new TankerPanel();
        panel.setVisible(true);
    }
}
