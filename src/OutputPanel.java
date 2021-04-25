import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;

public class OutputPanel extends JPanel {

    public OutputPanel(Window window, double target, Object[][] inputs, Object[][] cuttingPlan, String[] columnNames) {
        double total = cuttingPlan.length;
        double remainder = 0;
        for(int i = 0; i < cuttingPlan.length; i++) {
            String str = (String) cuttingPlan[i][cuttingPlan[i].length-1];
            remainder += Double.parseDouble(str);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Cutting Plan");
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);



        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));

        JLabel totalText = new JLabel("Total number of raw material:      " + cuttingPlan.length);
        JLabel remainderText = new JLabel("Total length of excess materials:  " + remainder);
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        totalText.setFont(labelFont);
        remainderText.setFont(labelFont);
        resultPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);


        //System.out.println("cutting plan: " + cuttingPlan.length + ", " + cuttingPlan[0].length);
        //System.out.println("column names: " + columnNames.length);

        // Set JTable with the data from parameter
        JTable table = new JTable(cuttingPlan, columnNames);

        // Set basic settings for table
        //table.setPreferredScrollableViewportSize(new Dimension(300, 300));
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));
        table.setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);                      // # column width (smallest)
        table.getColumnModel().getColumn(columnNames.length - 1).setPreferredWidth(50); // remainder column width (smaller)

        // Center the texts inside table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set JScrollPane for a scrollable table
        JScrollPane tablePanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int sideMargin = 50;
        if(cuttingPlan[0].length < 8) {
            sideMargin += 42 * (8 - cuttingPlan[0].length);
            System.out.println("side margin" + sideMargin);
        }
        tablePanel.setBorder(new EmptyBorder(5,sideMargin,5,sideMargin));
        GridLayout gridLayout = new GridLayout(1,2);
        gridLayout.setHgap(60);
        JPanel buttonsPanel = new JPanel(gridLayout);
        buttonsPanel.setBorder(new EmptyBorder(5, 150, 5, 150));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.changeToInputPanel();
            }
        });
        JButton printButton = new JButton("Excel");
        double finalRemainder = remainder;
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Printer.printOutput(target, inputs, total, finalRemainder, cuttingPlan, columnNames);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        resultPanel.add(totalText);
        resultPanel.add(remainderText);
        buttonsPanel.add(backButton);
        buttonsPanel.add(printButton);


        add(titleLabel);
        add(resultPanel);
        add(tablePanel );
        add(buttonsPanel);
    }
}
