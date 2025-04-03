package org.lab04;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DataPanel {

    DataPanel(String City, String Case, String Type) throws JsonProcessingException, JSONException {
        JFrame frame = new JFrame("UmówWizyte:" + City);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String jsonString = String.valueOf(ApiClient.fetchQueues(City, Case, Type));
        List<QueueEntry> entries = JsonParser.parse(jsonString);

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException ignored) {
        }
        frame.setSize(800, 400);
        frame.setLocation(100, 100);

        JPanel gui = new JPanel(new BorderLayout(5,5));
        gui.setBorder( BorderFactory.createLineBorder(Color.black));

        JPanel jpanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 3,3));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton button = new JButton("Powrót");
        jpanel.add(button);

        button.addActionListener(e->{
            frame.dispose();
            new MainPanel();
        });

        gui.add(jpanel, BorderLayout.NORTH);

        String[] header = {"Nazwa", "Adres", "Numer telefonu", "Data"};
        String[][] data = new String[entries.size()][4];
        for (int i=0; i< entries.size(); i++) {
            data[i][0] = entries.get(i).getProvider();
            data[i][1] = entries.get(i).getAddress() + " " + entries.get(i).getLocality();
            data[i][2] = entries.get(i).getPhone();
            data[i][3] = entries.get(i).getDate();
        }

        DefaultTableModel model = new DefaultTableModel(data, header);
        JTable table = new JTable(model);
        table.setEnabled(false);

        try {
            // 1.6+
            table.setAutoCreateRowSorter(true);
        } catch(Exception ignored) {
        }

        JScrollPane tableScroll = new JScrollPane(table);
        gui.add(tableScroll, BorderLayout.CENTER);

        frame.setContentPane(gui);
        frame.setVisible(true);
    }
}