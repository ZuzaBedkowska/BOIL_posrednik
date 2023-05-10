package logic;

import ui.MainUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            int n_suppliers = 2;
            int n_customers = 2;
            double[] supply = {45, 25};
            double[] demand = {30, 30};
            double[] purchasePrices = {6, 7};
            double[] sellPrices = {12, 13};
            double[][] transportCostsTable = {{7, 4}, {3, 5}};
            MainLogic mainLogic = new MainLogic(n_suppliers,n_customers,supply,demand,purchasePrices,sellPrices,transportCostsTable);
            mainLogic.test();
            //SwingUtilities.invokeLater(Main::createGUI);
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
        MainUI ui = new MainUI(); //klasa stworzona za pomoca kreatora GUI
        JPanel root = ui.getRootPanel(); //pobranie panelu
        JFrame frame = new JFrame(); //ramka na panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ustawienie zamykania ramki - guzik close
        frame.setContentPane(root); //dodanie panelu do ramki
        frame.pack(); //"spakowanie" ramki - dopasowanie jej rozmiaru do okienka
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null); //wysrodkowanie okienka na ekranie
        frame.setVisible(true); //wyswietlanie ui
    }
}