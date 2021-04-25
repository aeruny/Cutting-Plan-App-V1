import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {

    private JTextField rawMatInput;
    private InputTableModel model;
    private JTable table;


    public InputPanel(Window window) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        // Title
        JLabel titleLabel = new JLabel("Cutting Plan");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);


        // Raw Size
        JPanel rawMatPanel = new JPanel(new FlowLayout());
        JLabel rawMatText = new JLabel("The Length of the Raw Material: ");
        rawMatText.setFont(new Font("Helvetica", Font.BOLD, 16));
        rawMatInput = new JTextField(10);
        rawMatInput.setFont(new Font("Helvetica", Font.BOLD, 16));

        rawMatPanel.add(rawMatText);
        rawMatPanel.add(rawMatInput);

        rawMatInput.setText("");

        // Input Table
        model = new InputTableModel();
        model.addInput(new Input(1, 0, 0));
        //addDebuggingInputs();

        table = new JTable(model);
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
        table.setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setRowHeight(20);
        TableColumn column = null;


        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(5); //sport column is bigger
            } else {
                column.setPreferredWidth(200);
            }
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBorder(new EmptyBorder(5,50,5,50));



        // Buttons
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeRow();
            }
        });
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow();
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset TextField
                // reset Table
                // reset Table Size
                rawMatInput.setText("");
                model.resetRows();
            }
        });

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Plug the input values into the algorithm
                // Display the results onto OutputPanel
                // Display the cutting plan onto OutputPanel
                // change to OutputPanel
                if(isTableValid() && isTextFieldValid()) {
                    window.changeToOutputPanel();
                }
            }
        });


        JPanel controlPanel = new JPanel(new FlowLayout());
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(generateButton);

        add(titleLabel);
        add(rawMatPanel);
        add(controlPanel);
        add(tablePane);
        add(buttonsPanel);
    }


    public double getTarget() {
        System.out.println("Pushing Target");
        return Double.parseDouble(rawMatInput.getText());
    }

    public Object[][] getInputs() {
        System.out.println("Pushing Inputs");
        if(table.getRowCount() == 0)
            return null;
        Object[][] objects = new Object[table.getRowCount()][2];
        for(int i = 0; i < table.getRowCount(); i++) {
            for(int j = 0; j < 2; j++) {
                objects[i][j] = model.getValueAt(i, j + 1);
            }
        }
        return objects;
    }

    public boolean isTableValid() {
        for(int i = 0; i < table.getRowCount(); i++) {
            for(int j = 0; j < 2; j++) {
                if(model.getValueAt(i, j + 1) == "") {
                    System.err.println("Table: Empty Field Detected");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTextFieldValid() {
        if(rawMatInput.getText().isEmpty()) {
            System.err.println("Text Field: No Input Detected");
            return false;
        }
        return true;
    }

    public void addDebuggingInputs() {
        rawMatInput.setText("6");
        model.addInput(new Input(1, 5, 30));
        model.addInput(new Input(2, 3, 30));
        model.addInput(new Input(3, 1.75, 30));
        model.addInput(new Input(4, 1.5, 30));
    }
}
