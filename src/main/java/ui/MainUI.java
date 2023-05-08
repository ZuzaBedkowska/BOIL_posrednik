package ui;

import javax.swing.*;
import java.awt.*;

class TextField {
    JPanel panel;
    double val;
    int row;
    int col;
    public TextField(int row, int col, double val) {
        this.col = col;
        this.row = row;
        this.val = val;
        panel = new JPanel(new GridLayout(1,1));
    }
    public void makePanel(String desc) {
        if (!desc.equals("")) {
            panel.setLayout(new GridLayout(2,1));
            JLabel label = new JLabel(desc, SwingConstants.CENTER);
            panel.add(label);
        }
        JTextField jTextField = new JTextField(Double.toString(val));
        panel.add(jTextField);
    }
    public void setVisible(boolean b) {
        panel.setVisible(b);
    }
    public JPanel getPanel() {
        return panel;
    }
}

class GridComponent {
    int height; //ile dostawcow
    int heightWithButtons;
    int width; //ile odbiorcow
    int widthWithButtons;

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
        for (int rows = 0; rows < newHeight; ++rows) {
            for (int cols = 0; cols < newWidth; ++ cols) {
                //rog bez niczego
                TextField textField = new TextField(rows, cols,0);
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

    public JPanel getRootPanel(){
        return rootPanel;
    }

    public MainUI(){
        gridComponent = new GridComponent(2, 3);
        contentPane.setViewportView(gridComponent.getGridPanel());
        AddColumnButton = new JButton();
        AddRowButton = new JButton();
        RemoveColumnButton = new JButton();
        RemoveRowButton = new JButton();
        createAddColumnButton();;
        createRemoveColumnButton();
        createAddRowButton();
        createRemoveRowButton();
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
}
