package org.example;

import ui.MainUI1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            SwingUtilities.invokeLater(Main::createGUI);

        } catch (Exception e) {
            String message = "Something went wrong!\n";
            if (e.getMessage() != null) {
                message += e.getMessage();
                message += "\n";
            }
            message += "Please try again!\n";
            JOptionPane.showMessageDialog(new JFrame(), message);
        }
    }
    private static void createGUI() {
        MainUI1 ui = new MainUI1(); //klasa stworzona za pomoca kreatora GUI
        JPanel root = ui.getRootPanel(); //pobranie panelu
        JFrame frame = new JFrame(); //ramka na panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ustawienie zamykania ramki - guzik close
        frame.setContentPane(root); //dodanie panelu do ramki
        frame.pack(); //"spakowanie" ramki - dopasowanie jej rozmiaru do okienka
        frame.setSize(750, 750);
        frame.setLocationRelativeTo(null); //wysrodkowanie okienka na ekranie
        frame.setVisible(true); //wyswietlanie ui
    }
}