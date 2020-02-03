package dbproject;

import java.awt.*;

import javax.lang.model.element.TypeElement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableUI extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JLabel TypeLabel;
    private JTextField TypeTextField;

    public TableUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBounds(100, 100, 1007, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        TypeLabel = new JLabel();
        TypeLabel.setText("TypeLabel");
        TypeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        TypeLabel.setBounds(330, 18, 100, 35);
        contentPane.add(TypeLabel);


        TypeTextField = new JTextField();
        TypeTextField.setText("TypeTextField");
        TypeTextField.setColumns(20);
        TypeTextField.setBounds(490, 19, 200, 35);
        contentPane.add(TypeTextField);
        TypeTextField.setEditable(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 60, 993, 530);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null},
                },
                new String[]{
                        "THERE IS NO RECORD AS YOU REQUESTED"
                }
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        scrollPane.setViewportView(table);


    }

    public static void main(String[] args) {
        TableUI tableUI = new TableUI();
        tableUI.setVisible(true);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {

        this.table = table;
    }

    public void setTypeLabel(String str){
        TypeLabel.setText(str);
    }

    public void setTypeTextField(String str){
        TypeTextField.setText(str);
    }

    public JLabel getTypeLabel(){
        return TypeLabel;
    }

    public JTextField getTypeTextField(){
        return TypeTextField;
    }

}
