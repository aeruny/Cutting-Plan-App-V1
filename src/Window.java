import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final InputPanel inputPanel;
    private OutputPanel outputPanel;

    public Window() {
        setTitle("Cutting Plan V1.0 by Mingeon Sung");
        setPreferredSize(new Dimension(700,  500));

        inputPanel = new InputPanel(this);


        add(inputPanel);
        setContentPane(inputPanel);


        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void changeToInputPanel() {
        remove(outputPanel);
        setContentPane(inputPanel);
        getContentPane().revalidate();
        getContentPane().repaint();
        pack();
    }

    public void changeToOutputPanel() {
        double target = inputPanel.getTarget();
        Object[][] inputs = inputPanel.getInputs();
        double[][] cuttingPlan = CuttingPlan.getCuttingPlan(target, inputs);

        outputPanel = new OutputPanel(this, target, inputs, toObject(cuttingPlan), toNames(cuttingPlan));
        add(outputPanel);
        setContentPane(outputPanel);
        getContentPane().revalidate();
        getContentPane().repaint();
        pack();
    }

    public Object[][] toObject(double[][] plan) {
        Object[][] objects = new Object[plan.length][plan[0].length];
        for(int i = 0; i < plan.length; i++) {
            objects[i][0] = Integer.toString((int) plan[i][0]);
        }
        for(int i = 0; i < plan.length; i++) {
            for(int j = 1; j < plan[i].length; j++) {
                objects[i][j] = Double.toString(plan[i][j]);
            }
        }
        return objects;
    }

    public String[] toNames(double[][] plan) {
        int length = plan[0].length;
        String[] strings = new String[length];
        strings[0] = "#";
        for(int i = 1; i < length - 1; i++) {
            strings[i] = Integer.toString(i);
        }
        strings[length - 1] = "Remainder";
        return strings;
    }



}
