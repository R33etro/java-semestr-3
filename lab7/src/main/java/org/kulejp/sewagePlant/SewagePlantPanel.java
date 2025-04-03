package org.kulejp.sewagePlant;

import interfaces.ISewagePlant;

import javax.swing.*;
import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SewagePlantPanel extends JFrame {
    private JTextArea textArea;

    public SewagePlantPanel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            // handle exception
        }

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
        SewagePlantPanel panel = new SewagePlantPanel();
        panel.setVisible(true);
        try {

            String universalHost = "localhost";
            SewagePlant sewagePlant = new SewagePlant();
            ISewagePlant isp = (ISewagePlant) UnicastRemoteObject.exportObject(sewagePlant, 7000);
            Registry registry = LocateRegistry.getRegistry(universalHost, 2000);
            registry.rebind("SewagePlant", (Remote) isp);
            System.out.println("Sewage plant running on port 7000" );
        } catch (RemoteException eMSG) {
            System.out.println(eMSG.getMessage());
        }
    }
}
