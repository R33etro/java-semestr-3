package org.kulejp.office;

import interfaces.IOffice;
import interfaces.ISewagePlant;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class OfficePanel extends JFrame{
//    private JFrame frame;

    private JTextField tankerNumberField;
    private JTextArea logArea;
    private Office office;

    public OfficePanel(Office office) throws RemoteException, NotBoundException {
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
            try {
                office.sendRequestGETSTATUS(Integer.parseInt(tankerNumber));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            logArea.append("Get Status request sent for tanker " + tankerNumber + "\n");
        });

        setPayoffButton.addActionListener(e -> {
            String tankerNumber = tankerNumberField.getText();
            try {
                office.sendRequestSETPAYOFF(Integer.parseInt(tankerNumber));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            logArea.append("Set Payoff request sent for tanker " + tankerNumber + "\n");
        });

    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        String universalHost = "localhost";
        Registry registry = LocateRegistry.getRegistry(universalHost, 2000);
        ISewagePlant iSewagePlant = (ISewagePlant) registry.lookup("SewagePlant");
        Office office = new Office(iSewagePlant);
        IOffice io = (IOffice) UnicastRemoteObject.exportObject(office, 6000);
        registry.rebind("Office", io);

        System.out.println("Ofiice running on port 6000" );

        OfficePanel panel = new OfficePanel(office);
        panel.setVisible(true);
    }
}
