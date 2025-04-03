package org.lab04;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import javax.swing.*;
import java.util.Objects;

public class MainPanel {
    JFrame f;
    MainPanel() {
        f =new JFrame("UmówWizyteApp");

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException ignored) {
        }

        final JLabel label = new JLabel();
        label.setText("Wybierz miasto:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);

        String[] cities ={"Wrocław","Kraków"};
        final JComboBox cb=new JComboBox(cities);
        cb.setBounds(150,70,100,40);

        ButtonGroup bg1=new ButtonGroup();
        JRadioButton r11 = new JRadioButton("Pilne", true);
        JRadioButton r12 = new JRadioButton("Nie pilne",  false);
        bg1.add(r11);
        bg1.add(r12);
        r11.setBounds(120,120,100,40);
        r12.setBounds(222,120,100,40);

        ButtonGroup bg2=new ButtonGroup();
        JRadioButton r21 = new JRadioButton("Stomatologia", true);
        JRadioButton r22 = new JRadioButton("Ortodoncja", false);
        bg2.add(r21);
        bg2.add(r22);
        r21.setBounds(120,150,100,40);
        r22.setBounds(222,150,100,40);

        JButton b=new JButton("Idź");
        b.setBounds(160,200,80,30);


        f.add(cb); f.add(label); f.add(b); f.add(r11); f.add(r12); f.add(r21); f.add(r22);
        f.setLayout(null);
        f.setSize(400,300);
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b.addActionListener(e -> {
            String Data = cb.getItemAt(cb.getSelectedIndex()).toString();
            String Case = bg1.getSelection().getActionCommand();
            if(Objects.equals(Case, "Pilne")) Case = "01";
                    else Case = "02";
            String Type = bg2.getSelection().getActionCommand();
            if(Objects.equals(Type, "Stomatologia")) Type = "Stomatologiczna";
            else Type = "Ortodon";
            try {
                new DataPanel(Data, Case, Type);
            } catch (JsonProcessingException | JSONException ex) {
                throw new RuntimeException(ex);
            }

            f.setVisible(false);
        });
    }
}