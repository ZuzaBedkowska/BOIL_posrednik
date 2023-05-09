package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class DataFetcher {
    private int dostawcy;
    private int odbiorcy;
    private ArrayList<Double> popyt;
    private ArrayList<Double> podaz;
    private ArrayList<Double> cena;
    private ArrayList<Double> koszt;
    private ArrayList<ArrayList<Double>> transport;
    private ArrayList<ArrayList<Double>> result;
    public DataFetcher(int dostawcy, int odbiorcy) {
        this.dostawcy = dostawcy;
        this.odbiorcy = odbiorcy;
        cena = new ArrayList<>(Collections.nCopies(odbiorcy, 0.));
        popyt = new ArrayList<>(Collections.nCopies(odbiorcy, 0.));
        koszt = new ArrayList<>(Collections.nCopies(dostawcy, 0.));
        podaz = new ArrayList<>(Collections.nCopies(dostawcy, 0.));
        transport = new ArrayList<>(dostawcy);
        result = new ArrayList<>();
        for (int i = 0; i< dostawcy; ++i) {
            transport.add(new ArrayList<>(Collections.nCopies(odbiorcy, 0.)));
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
        result = new ArrayList<>(dostawcy);
        for (int i = 0; i< dostawcy; ++i) {
            result.add(new ArrayList<>());
            for (int j = 0; j< odbiorcy; ++j) {
                result.get(i).add(i + j/10.0);
            }
        }
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

    public ArrayList<ArrayList<Double>> getResult() {
        return result;
    }

    public int getDostawcy() {
        return dostawcy;
    }

    public int getOdbiorcy() {
        return odbiorcy;
    }
}

class TextField {
    private JPanel panel;
    private double val;
    private int row;
    private int col;

    JTextField jTextField;

    public TextField(int row, int col, double val) {
        this.col = col;
        this.row = row;
        this.val = val;
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

    private JButton AddColumnButton;
    private JButton RemoveColumnButton;
    private JButton AddRowButton;
    private JButton RemoveRowButton;
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
                    desc = "Cena dla Odbiorcy " + Integer.toString(cols-1);
                } else if(rows == 1 && cols < newWidth-1){
                    desc = "Popyt Odbiorcy " + Integer.toString(cols-1);
                } else if(cols == 0 && rows < newHeight-1){
                    desc = "Koszt dla Dostawcy  " + Integer.toString(rows-1);
                } else if(cols == 1 && rows < newHeight-1){
                    desc = "Podaż Dostawcy  " + Integer.toString(rows-1);
                } else if (cols == newWidth-1) { //guzik dodaj
                    if (rows ==0) {
                        gridPanel2.add(AddColumnButton);
                        continue;
                    } else if (rows == 1) {
                        if (width == 3) {
                            RemoveColumnButton.setEnabled(false);
                        } else {
                            RemoveColumnButton.setEnabled(true);
                        }
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
                        if (height == 2) {
                            RemoveRowButton.setEnabled(false);
                        } else {
                            RemoveRowButton.setEnabled(true);
                        }
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

public class MainUI {
    private JPanel rootPanel;
    private JButton displayButton;
    private JScrollPane contentPane;
    private GridComponent gridComponent;
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
        createAddColumnButton();;
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
        int row = 0;
        int col = 0;
        String message = "\n";
        for (ArrayList<TextField> l: gridComponent.getTextFields()) {
            for (TextField t : l) {
                double num = 0;
                try {
                    num = Double.parseDouble(t.getjTextField().getText());
                } catch (NumberFormatException nfe) {
                    if (t.getRow() == 0) {
                        message+= "Podana cena dla Odbiorcy " + (t.getCol() - 1) + " nie jest liczbą!";
                    } else if (t.getRow() == 1) {
                        message+= "Podany popyt dla Odbiorcy " + (t.getCol() - 1) + " nie jest liczbą!";
                    } else if (t.getCol()==0) {
                        message+= "Podany koszt dla Dostawcy " + (t.getRow() - 1) + " nie jest liczbą!";
                    } else if (t.getCol() == 1) {
                        message+= "Podana podaż dla Dostawcy " + (t.getRow() - 1) + " nie jest liczbą!";
                    } else {
                        message+= "Podany koszt transportu między Dostawcą " + (t.getRow() - 1) + " a Odbiorcą " + (t.getCol() - 1) + " nie jest liczbą!";
                    }
                }
                if (num < 0) {
                    message += "Czas wykonania zadania jest mniejszy od 0!\n";
                }
                if (!message.equals("\n")) {
                    throw new Exception(message);
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
        JPanel panel = new JPanel(new GridLayout(dataFetcher.getDostawcy() + 1, dataFetcher.getOdbiorcy() + 1, 5, 5));
        //generuj macierz
        for (int i = 0; i < dataFetcher.getDostawcy() + 1; ++i) {
            for (int j = 0; j < dataFetcher.getOdbiorcy() + 1; ++j) {
                JLabel label = new JLabel("");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                if (i == 0 && j != 0){
                    label.setText("O" + j);
                } else if (j == 0 && i != 0) {
                    label.setText("D" + i);
                } else if (i != 0 && j != 0){
                    label.setText(Double.toString(dataFetcher.getResult().get(i - 1).get(j - 1)));
                    label.setBorder(new EtchedBorder());
                    label.setOpaque(true);
                    label.setBackground(Color.white);
                }
                panel.add(label);
            }
        }
        scrollPane.setViewportView(panel);
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
