package ui;

import logic.MainLogic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class DataFetcher {

    private MainLogic logic;
    private final int dostawcy;
    private final int odbiorcy;
    private final ArrayList<Double> popyt;
    private final ArrayList<Double> podaz;
    private final ArrayList<Double> cena;
    private final ArrayList<Double> koszt;
    private final ArrayList<ArrayList<Double>> transport;
    private ArrayList<ArrayList<Double>> optTransport;
    private ArrayList<ArrayList<Double>> profit;
    public DataFetcher(int dostawcy, int odbiorcy) {
        this.dostawcy = dostawcy;
        this.odbiorcy = odbiorcy;
        cena = new ArrayList<>(Collections.nCopies(odbiorcy, 0.));
        popyt = new ArrayList<>(Collections.nCopies(odbiorcy, 0.));
        koszt = new ArrayList<>(Collections.nCopies(dostawcy, 0.));
        podaz = new ArrayList<>(Collections.nCopies(dostawcy, 0.));
        transport = new ArrayList<>(dostawcy);
        optTransport = new ArrayList<>(dostawcy);
        profit = new ArrayList<>(dostawcy);
        for (int i = 0; i< dostawcy; ++i) {
            transport.add(new ArrayList<>(Collections.nCopies(odbiorcy, 0.)));
            optTransport.add(new ArrayList<>(Collections.nCopies(odbiorcy, 0.)));
            profit.add(new ArrayList<>(Collections.nCopies(odbiorcy, 0.)));
        }
    }

    public void parseData(GridComponent gridComponent) {
        for (ArrayList<TextField> l : gridComponent.getTextFields()) {
            for (TextField t: l) {
                if (Double.parseDouble(t.getjTextField().getText())!=0.) {
                    if (t.getRow() == 0) {
                        this.setCena(t.getCol() - 2, Double.parseDouble(t.getjTextField().getText()));
                    } else if (t.getRow() == 1) {
                        this.setPopyt(t.getCol() - 2, Double.parseDouble(t.getjTextField().getText()));
                    } else if (t.getCol() == 0) {
                        this.setKoszt(t.getRow() - 2, Double.parseDouble(t.getjTextField().getText()));
                    } else if (t.getCol() == 1) {
                        this.setPodaz(t.getRow() - 2, Double.parseDouble(t.getjTextField().getText()));
                    } else {
                        this.setTransport(t.getRow() - 2, t.getCol() - 2, Double.parseDouble(t.getjTextField().getText()));
                    }
                }
            }
        }
    }

    public void optimizeTransport() {
        //tutaj wywolanie obliczenia
        //do testow
        logic = new MainLogic(dostawcy, odbiorcy, listConverter(podaz), listConverter(popyt), listConverter(koszt), listConverter(cena), matrixConverter(transport));
        logic.calc_northWestCorner();
        optTransport = matrixConverter(logic.getOptimal_transport());
        profit = matrixConverter(logic.getIndividual_profit());

    }

    private ArrayList<ArrayList<Double>> matrixConverter(double [][] array) {
        ArrayList<ArrayList<Double>> list = new ArrayList<>();
        for (double[] doubles : array) {
            ArrayList<Double> innerList = new ArrayList<>();
            for (double aDouble : doubles) {
                innerList.add(aDouble);
            }
            list.add(innerList);
        }
        return list;
    }

    private double[][] matrixConverter(ArrayList<ArrayList<Double>> list) {
        double [][] res = new double[list.size()][list.get(0).size()];
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < list.get(0).size(); ++j) {
                res[i][j] = list.get(i).get(j);
            }
        }
        return res;
    }

    private double[] listConverter(ArrayList<Double> list) {
        double [] res = new double[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            res[i] = list.get(i);
        }
        return res;
    }
    public ArrayList<Double> getPopyt() {
        return popyt;
    }

    public void setPopyt(int index, double value) {
        this.popyt.set(index, value);
    }

    public ArrayList<Double> getPodaz() {
        return podaz;
    }

    public void setPodaz(int index, double value) {
        this.podaz.set(index, value);
    }

    public ArrayList<Double> getCena() {
        return cena;
    }

    public void setCena(int index, double value) {
        this.cena.set(index, value);
    }

    public ArrayList<Double> getKoszt() {
        return koszt;
    }

    public void setKoszt(int index, double value) {
        this.koszt.set(index, value);
    }

    public ArrayList<ArrayList<Double>> getTransport() {
        return transport;
    }

    public void setTransport(int row, int col, double value) {
        this.transport.get(row).set(col, value);
    }

    public ArrayList<ArrayList<Double>> getOptTransport() {
        return optTransport;
    }

    public ArrayList<ArrayList<Double>> getProfit() {
        return profit;
    }

    public MainLogic getLogic() {
        return logic;
    }

    public int getDostawcy() {
        return dostawcy;
    }

    public int getOdbiorcy() {
        return odbiorcy;
    }
}

class TextField {
    private final JPanel panel;
    private final int row;
    private final int col;

    JTextField jTextField;

    public TextField(int row, int col, double val) {
        this.col = col;
        this.row = row;
        jTextField = new JTextField(Double.toString(val));
        jTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel = new JPanel(new GridLayout(1,1));
    }
    public void makePanel(String desc) {
        if (!desc.equals("")) {
            panel.setLayout(new GridLayout(2,1));
            JLabel label = new JLabel(desc, SwingConstants.CENTER);
            panel.add(label);
        }
        panel.add(jTextField);
    }
    public void setVisible(boolean b) {
        panel.setVisible(b);
    }
    public JPanel getPanel() {
        return panel;
    }

    public JTextField getjTextField() {
        return jTextField;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

class GridComponent {
    private int height; //ile dostawcow
    private int heightWithButtons;
    private int width; //ile odbiorcow
    private int widthWithButtons;

    private ArrayList<ArrayList<TextField>> textFields;

    private final JButton AddColumnButton;
    private final JButton RemoveColumnButton;
    private final JButton AddRowButton;
    private final JButton RemoveRowButton;
    JPanel gridPanel;
    public GridComponent(int height, int width) {
        this.height = height;
        this.heightWithButtons = height+3; //dodane 2 pola u gory i 1 na dole
        this.width = width;
        this.widthWithButtons = width+3; //dodane 2 pola po lewej i 1 po prawej
        this.textFields = new ArrayList<>(heightWithButtons);
        for (int i = 0; i < heightWithButtons; ++i) {
            textFields.add(new ArrayList<>());
        }
        AddColumnButton = new JButton("Dodaj Odbiorcę");
        RemoveColumnButton = new JButton("Usuń Odbiorcę");
        AddRowButton = new JButton("Dodaj Dostawcę");
        RemoveRowButton = new JButton("Usuń Dostawcę");
        gridPanel = new JPanel();
        refresh(heightWithButtons, widthWithButtons);
    }

    public void refresh(int newHeight, int newWidth) {
        JPanel gridPanel2 = new JPanel();
        GridLayout layout = new GridLayout(newHeight, newWidth, 5, 5);
        gridPanel2.setLayout(layout);
        ArrayList<ArrayList<TextField>> textFields2 = new ArrayList<>(newHeight);
        for (int i = 0; i < heightWithButtons; ++i) {
            textFields2.add(new ArrayList<>());
        }
        for (int rows = 0; rows < newHeight; ++rows) {
            for (int cols = 0; cols < newWidth; ++ cols) {
                //rog bez niczego
                TextField textField = new TextField(rows, cols,0);
                textFields2.get(rows).add(textField);
                String desc = "";
                if (rows < 2 && cols < 2) {
                    textField.setVisible(false);
                } else if(rows == 0 && cols < newWidth-1){
                    desc = "Cena sprzedaży Odbiorcy " + (cols - 1);
                } else if(rows == 1 && cols < newWidth-1){
                    desc = "Popyt Odbiorcy " + (cols - 1);
                } else if(cols == 0 && rows < newHeight-1){
                    desc = "Koszt zakupu Dostawcy  " + (rows - 1);
                } else if(cols == 1 && rows < newHeight-1){
                    desc = "Podaż Dostawcy  " + (rows - 1);
                } else if (cols == newWidth-1) { //guzik dodaj
                    if (rows ==0) {
                        gridPanel2.add(AddColumnButton);
                        continue;
                    } else if (rows == 1) {
                        RemoveColumnButton.setEnabled(width != 1);
                        gridPanel2.add(RemoveColumnButton);
                        continue;
                    } else {
                        textField.setVisible(false);
                    }
                } else if (rows == newHeight - 1) {
                    if (cols == 0) {
                        gridPanel2.add(AddRowButton);
                        continue;
                    } else if (cols ==1) {
                        RemoveRowButton.setEnabled(height != 1);
                        gridPanel2.add(RemoveRowButton);
                        continue;
                    } else {
                        textField.setVisible(false);
                    }
                }
                textField.makePanel(desc);
                gridPanel2.add(textField.getPanel());
            }
        }
        textFields = textFields2;
        gridPanel = gridPanel2;
    }

    public void addColumn() {
        width++;
        widthWithButtons++;
        refresh(heightWithButtons, widthWithButtons);
    }

    public void removeColumn() {
        width--;
        widthWithButtons--;
        refresh(heightWithButtons, widthWithButtons);
    }

    public void addRow() {
        height++;
        heightWithButtons++;
        refresh(heightWithButtons, widthWithButtons);
    }

    public void removeRow() {
        height--;
        heightWithButtons--;
        refresh(heightWithButtons, widthWithButtons);
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JButton getAddColumnButton(){
        return AddColumnButton;
    }

    public JButton getRemoveColumnButton() {
        return RemoveColumnButton;
    }

    public JButton getAddRowButton() {
        return AddRowButton;
    }

    public JButton getRemoveRowButton() {
        return RemoveRowButton;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<ArrayList<TextField>> getTextFields() {
        return textFields;
    }
}

class LittleGrid {
    private final JPanel mainPanel;
    public LittleGrid(){
        mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
    }
    public void addGrid(int r, int c, String title, ArrayList<ArrayList<Double>> result) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        panel.add(titleLabel);
        JPanel gridPanel = new JPanel(new GridLayout(result.size() + 1, result.get(0).size() + 1, 5, 5));
        //generuj macierz
        for (int i = 0; i < result.size() + 1; ++i) {
            for (int j = 0; j < result.get(0).size() + 1; ++j) {
                JLabel label = new JLabel("");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                if (i == 0 && j != 0){
                    if (c < j)
                        label.setText("FO");
                    else
                        label.setText("O" + j);
                } else if (j == 0 && i != 0) {
                    if (r < i)
                        label.setText("FD");
                    else
                        label.setText("D" + i);
                } else if (i != 0){
                    label.setText(Double.toString(result.get(i - 1).get(j - 1)));
                    label.setBorder(new EtchedBorder());
                    label.setOpaque(true);
                    label.setBackground(Color.white);
                }
                gridPanel.add(label);
            }
        }
        panel.add(gridPanel);
        mainPanel.add(panel);
    }

    public void addNumData(MainLogic logic){
        JPanel numPanel = new JPanel(new GridLayout(6,1, 5, 5));
        JLabel label = new JLabel("Parametry opisujące optymalny plan transportu");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 15));
        numPanel.add(label);
        numPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        numPanel.add(new JLabel("<html>&#8226;Całkowity koszt transportu: " + (logic.getCost_transport()) + "<html>"));
        numPanel.add(new JLabel("<html>&#8226;Całkowity koszt zakupu: " +(logic.getCost_purchase()) + "<html>"));
        numPanel.add(new JLabel("<html>&#8226;Koszt całkowity: "+(logic.getCost_purchase()) +
                " + " + (logic.getCost_transport()) + " = " + (logic.getCost_transport() + logic.getCost_purchase())  + "<html>"));
        numPanel.add(new JLabel("<html>&#8226;Całkowity zysk ze sprzedaży: " + logic.getIncome_from_sell() + "<html>"));
        numPanel.add(new JLabel("<html>&#8226;Całkowity zysk z wykonania planu transportu: " +
                logic.getIncome_from_sell() + " - " + (logic.getCost_transport() + logic.getCost_purchase()) + " = " +
                (logic.getIncome_from_sell() - (logic.getCost_transport() + logic.getCost_purchase()))  + "<html>"));
        mainPanel.add(numPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

public class MainUI {
    private JPanel rootPanel;
    private JButton displayButton;
    private JScrollPane contentPane;
    private final GridComponent gridComponent;
    private JButton AddColumnButton;
    private JButton RemoveColumnButton;
    private JButton AddRowButton;
    private JButton RemoveRowButton;
    private DataFetcher dataFetcher;

    public JPanel getRootPanel(){
        return rootPanel;
    }
    public MainUI(){
        gridComponent = new GridComponent(2, 3);
        dataFetcher = new DataFetcher(gridComponent.getHeight(), gridComponent.getWidth());
        contentPane.setViewportView(gridComponent.getGridPanel());
        AddColumnButton = new JButton();
        AddRowButton = new JButton();
        RemoveColumnButton = new JButton();
        RemoveRowButton = new JButton();
        createDisplayButton();
        createAddColumnButton();
        createRemoveColumnButton();
        createAddRowButton();
        createRemoveRowButton();
    }
    public void createDisplayButton(){
        displayButton.addActionListener(e -> this.computeData());
    }
    public void computeData() {
        try {
            this.checkData();
            dataFetcher = new DataFetcher(gridComponent.getHeight(), gridComponent.getWidth());
            dataFetcher.parseData(gridComponent);
            dataFetcher.optimizeTransport();
            this.displayResult();
        } catch (Exception e) {
            errorWindow(e);
        }
    }
    public void checkData() throws Exception {
        StringBuilder message = new StringBuilder("\n");
        displayData();
        for (ArrayList<TextField> l: gridComponent.getTextFields()) {
            for (TextField t : l) {
                double num = 0;
                try {
                    num = Double.parseDouble(t.getjTextField().getText());
                } catch (NumberFormatException nfe) {
                    if (t.getRow() == 0) {
                        message.append("Podana cena dla Odbiorcy ").append(t.getCol() - 1).append(" nie jest liczbą!");
                    } else if (t.getRow() == 1) {
                        message.append("Podany popyt dla Odbiorcy ").append(t.getCol() - 1).append(" nie jest liczbą!");
                    } else if (t.getCol()==0) {
                        message.append("Podany koszt dla Dostawcy ").append(t.getRow() - 1).append(" nie jest liczbą!");
                    } else if (t.getCol() == 1) {
                        message.append("Podana podaż dla Dostawcy ").append(t.getRow() - 1).append(" nie jest liczbą!");
                    } else {
                        message.append("Podany koszt transportu między Dostawcą ").append(t.getRow() - 1).append(" a Odbiorcą ").append(t.getCol() - 1).append(" nie jest liczbą!");
                    }
                }
                if (num < 0) {
                    message.append("Czas wykonania zadania jest mniejszy od 0!\n");
                }
                if (!message.toString().equals("\n")) {
                    throw new Exception(message.toString());
                }
            }
        }
    }
    public void displayData() {
        for (double d: dataFetcher.getCena()) {
            System.out.print(d + " ");
        }
        System.out.println();
        for (double d: dataFetcher.getPopyt()) {
            System.out.print(d + " ");
        }
        System.out.println();
        System.out.println();
        for (double d: dataFetcher.getKoszt()) {
            System.out.print(d + " ");
        }
        System.out.println();
        System.out.println();
        for (double d: dataFetcher.getPodaz()) {
            System.out.print(d + " ");
        }
        System.out.println();
        System.out.println();
        for (ArrayList<Double> l: dataFetcher.getTransport()) {
            for (double d: l) {
                System.out.print(d + " ");
            }
        }
        System.out.println();
    }
    public void displayResult(){
        String title = "Optymalny transport";
        JScrollPane scrollPane = new JScrollPane();
        LittleGrid littleGrid = new LittleGrid();
        littleGrid.addGrid(dataFetcher.getDostawcy(), dataFetcher.getOdbiorcy(), "Zysk z transportu jednej sztuki towaru od danego dostawcy, do danego odbiorcy", dataFetcher.getProfit());
        littleGrid.addGrid(dataFetcher.getDostawcy(), dataFetcher.getOdbiorcy(),"Optymalny plan transportu", dataFetcher.getOptTransport());
        littleGrid.addNumData(dataFetcher.getLogic());
        scrollPane.setViewportView(littleGrid.getMainPanel());
        scrollPane.setSize(new Dimension(1000, 1000));
        scrollPane.setPreferredSize(new Dimension(700, scrollPane.getPreferredSize().height));
        JOptionPane.showConfirmDialog(null, scrollPane, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    }
    public void createAddColumnButton() {
        AddColumnButton = gridComponent.getAddColumnButton();
        AddColumnButton.addActionListener(e -> {
            gridComponent.addColumn();
            contentPane.setViewportView(gridComponent.getGridPanel());
        });
    }
    public void createRemoveColumnButton() {
        RemoveColumnButton = gridComponent.getRemoveColumnButton();
        RemoveColumnButton.addActionListener(e -> {
            gridComponent.removeColumn();
            contentPane.setViewportView(gridComponent.getGridPanel());
        });
    }
    public void createAddRowButton() {
        AddRowButton = gridComponent.getAddRowButton();
        AddRowButton.addActionListener(e -> {
            gridComponent.addRow();
            contentPane.setViewportView(gridComponent.getGridPanel());
        });
    }
    public void createRemoveRowButton() {
        RemoveRowButton = gridComponent.getRemoveRowButton();
        RemoveRowButton.addActionListener(e -> {
            gridComponent.removeRow();
            contentPane.setViewportView(gridComponent.getGridPanel());
        });
    }
    public void errorWindow(Exception e) {
        String message = "Coś poszło nie tak!\n";
        if (e.getMessage() != null) {
            message += e.getMessage();
            message += "\n";
        }
        message += "Spróbuj ponownie!\n";
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
