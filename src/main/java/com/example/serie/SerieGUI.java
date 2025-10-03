package com.example.serie;

import javax.swing.*;
import java.awt.*;
public class SerieGUI extends JFrame {

    private JTextArea textArea;

    public SerieGUI() {
        setTitle("serie de tres en tres .");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //area de texto con estilos .
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.BOLD, 18));
        textArea.setForeground(new Color(0, 128, 0));
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    //metodo que genera la serie de 0 a 100 :. en pasos de 3 .
    private void generarSerie() {
        textArea.setText("");
        for (int i=0; i<=100; i+=3) {
            textArea.append(i + "\n");
            DBConnection.insertarNumero(i); //guardar en :. oracle .
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SerieGUI gui = new SerieGUI();
            gui.setVisible(true);
        });
    }
}
