package logic;

import ui.MainUI;

import javax.swing.*;

public class Main {
    /*
            int n_suppliers = 2;
            int n_customers = 2;
            double[] supply = {45, 25};
            double[] demand = {30, 30};
            double[] purchasePrices = {6, 7};
            double[] sellPrices = {12, 13};
            double[][] transportCostsTable = {{7, 4}, {3, 5}};
     */
    /*
            //https://cbom.atozmath.com/CBOM/Transportation.aspx?q=vam&q1=-11%2c-13%2c-17%2c-14%2c-42%2c-24%2c-42%2c0%3b-16%2c-18%2c-14%2c-10%2c-43%2c-23%2c-34%2c0%3b-21%2c-24%2c-13%2c-10%2c-42%2c-23%2c-23%2c0%3b-23%2c-43%2c-23%2c-43%2c-54%2c-74%2c-34%2c0%3b-12%2c-45%2c-74%2c-23%2c-53%2c-23%2c-23%2c0%3b-43%2c-23%2c-53%2c-34%2c-52%2c-52%2c-34%2c0%3b-23%2c-23%2c-34%2c-34%2c-23%2c-34%2c-23%2c0%3b-76%2c-56%2c-67%2c-75%2c-45%2c-65%2c-34%2c0%3b-56%2c-45%2c-34%2c-23%2c-34%2c-54%2c-34%2c0%3b0%2c0%2c0%2c0%2c0%2c0%2c0%2c0%60110%2c120%2c130%2c140%2c150%2c160%2c170%2c180%2c190%2c1680%60210%2c220%2c230%2c240%2c250%2c260%2c270%2c1350%60S1%2cS2%2cS3%2cS4%2cS5%2cS6%2cS7%2cS8%2cS9%2cSdummy%60D1%2cD2%2cD3%2cD4%2cD5%2cD6%2cD7%2cDdummy%60vam%60false%60false%60MAX&do=1#PrevPart
            int n_suppliers = 9;
            int n_customers = 7;
            double[] supply = {110, 120, 130, 140, 150, 160, 170, 180, 190};
            double[] demand = {210, 220, 230, 240, 250, 260, 270};
            double[] purchasePrices = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            double[] sellPrices = {0, 0, 0, 0, 0, 0, 0};
            double[][] transportCostsTable = {
                    {11, 13, 17, 14, 42, 24, 42},
                    {16, 18, 14, 10, 43, 23, 34},
                    {21, 24, 13, 10, 42, 23, 23},
                    {23, 43, 23, 43, 54, 74, 34},
                    {12, 45, 74, 34, 53, 23, 23},
                    {43, 23, 53, 34, 52, 52, 34},
                    {23, 23, 34, 34, 23, 34, 23},
                    {76, 56, 67, 75, 45, 65, 34},
                    {56, 45, 34, 23, 34, 54, 34}
            };
     */
    /*
            //https://cbom.atozmath.com/CBOM/Transportation.aspx?q=vam&q1=-1%2c-42%2c45%2c109%2c0%3b3%2c-38%2c57%2c122%2c0%3b-43%2c-85%2c17%2c81%2c0%3b-16%2c-75%2c36%2c77%2c0%3b24%2c-48%2c14%2c115%2c0%3b-68%2c-87%2c-26%2c54%2c0%3b0%2c0%2c0%2c0%2c0%60110%2c120%2c130%2c140%2c150%2c160%2c900%60210%2c220%2c230%2c240%2c810%60S1%2cS2%2cS3%2cS4%2cS5%2cS6%2cSdummy%60D1%2cD2%2cD3%2cD4%2cDdummy%60vam%60false%60false%60MAX&do=1#PrevPart
            int n_customers = 4;
            int n_suppliers = 6;
            double[] demand = {210, 220, 230, 240};
            double[] supply = {110, 120, 130, 140, 150, 160};
            double[] sellPrices = {131, 92, 183, 244};
            double[] purchasePrices = {121, 112, 153, 124, 95, 156};
            double[][] transportCostsTable = {
                    {11, 13, 17, 14},
                    {16, 18, 14, 10},
                    {21, 24, 13, 10},
                    {23, 43, 23, 43},
                    {12, 45, 74, 34},
                    {43, 23, 53, 34}
            };
     */
    public static void main(String[] args) {
        try{
            //https://cbom.atozmath.com/CBOM/Transportation.aspx?q=vam&q1=-1%2c-42%2c45%2c109%2c0%3b3%2c-38%2c57%2c122%2c0%3b-43%2c-85%2c17%2c81%2c0%3b-16%2c-75%2c36%2c77%2c0%3b24%2c-48%2c14%2c115%2c0%3b-68%2c-87%2c-26%2c54%2c0%3b0%2c0%2c0%2c0%2c0%60110%2c120%2c130%2c140%2c150%2c160%2c900%60210%2c220%2c230%2c240%2c810%60S1%2cS2%2cS3%2cS4%2cS5%2cS6%2cSdummy%60D1%2cD2%2cD3%2cD4%2cDdummy%60vam%60false%60false%60MAX&do=1#PrevPart
            int n_customers = 4;
            int n_suppliers = 6;
            double[] demand = {210, 220, 230, 240};
            double[] supply = {110, 120, 130, 140, 150, 160};
            double[] sellPrices = {131, 92, 183, 244};
            double[] purchasePrices = {121, 112, 153, 124, 95, 156};
            double[][] transportCostsTable = {
                    {11, 13, 17, 14},
                    {16, 18, 14, 10},
                    {21, 24, 13, 10},
                    {23, 43, 23, 43},
                    {12, 45, 74, 34},
                    {43, 23, 53, 34}
            };
            MainLogic mainLogic = new MainLogic(n_suppliers,n_customers,supply,demand,purchasePrices,sellPrices,transportCostsTable);
            mainLogic.calc();
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