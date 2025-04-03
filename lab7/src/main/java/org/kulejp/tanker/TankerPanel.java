package org.kulejp.tanker;

import interfaces.IOffice;
import interfaces.ISewagePlant;
import interfaces.ITanker;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TankerPanel extends JFrame {
    private JFrame frame;
    private JTextField maxVolField;
    private JTextField tankerNameField;
    private JTextField registryPortField;
    private JTextArea logArea;
    private int port = 4000;
    private Tanker tanker;

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
        registryPortField = new JTextField(5);
        tankerNameField = new JTextField(10);

        JButton registerButton = new JButton("Register");

        tankerPanel.add(new JLabel("Max Vol"));
        tankerPanel.add(maxVolField);
        tankerPanel.add(new JLabel("Name"));
        tankerPanel.add(tankerNameField);
        tankerPanel.add(new JLabel("Register Port"));
        tankerPanel.add(registryPortField);

        tankerPanel.add(registerButton);

        topPanel.add(tankerPanel);
        add(topPanel, BorderLayout.NORTH);


        registerButton.addActionListener(e -> {
            int registryPort = Integer.parseInt(registryPortField.getText());
            int maxVol = Integer.parseInt(maxVolField.getText());
            String name = tankerNameField.getText();
            int tankerPort = port;
            this.port++;
            String universalHost = "localhost";
            try {
                Registry registry = LocateRegistry.getRegistry(registryPort);
                IOffice iOffice = (IOffice) registry.lookup("Office");
                ISewagePlant iSewagePlant = (ISewagePlant) registry.lookup("SewagePlant");
                Tanker tanker = new Tanker(maxVol, iSewagePlant, iOffice, name);
                ITanker iTanker = (ITanker) UnicastRemoteObject.exportObject((Remote) tanker, tankerPort);
                registry = LocateRegistry.getRegistry(universalHost, registryPort);
                registry.rebind(name, (Remote) iTanker);
                tanker.registerTanker((interfaces.ITanker) iTanker, name);

            } catch (NotBoundException | RemoteException eMSG) {
                System.out.println(eMSG.getMessage());
            }

        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TankerPanel::new);
        TankerPanel panel = new TankerPanel();
        panel.setVisible(true);
    }
}
