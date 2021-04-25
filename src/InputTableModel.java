import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class InputTableModel extends AbstractTableModel {

    public static final int INDEX_COLUMN = 0;
    public static final int LENGTH_COLUMN = 1;
    public static final int QUANTITY_COLUMN = 2;

    private Vector inputs = new Vector();

    public void addInput(Input input) {
        inputs.addElement(input);
        int index = inputs.size() - 1;
        fireTableRowsInserted(index, index);
    }
       

    @Override
    public int getRowCount() {
        return inputs.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    public Class getColumnClass(int columnIndex) {
        if(columnIndex >= INDEX_COLUMN && columnIndex <= QUANTITY_COLUMN)
            return String.class;
        return null;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case INDEX_COLUMN:
                return "#";
            case LENGTH_COLUMN:
                return "Length";
            case QUANTITY_COLUMN:
                return "Quantity";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Input input = (Input) inputs.elementAt(rowIndex);
        switch (columnIndex) {
            case INDEX_COLUMN:
                return input.getIndex();
            case LENGTH_COLUMN:
                return input.getLength();
            case QUANTITY_COLUMN:
                return input.getQuantity();
            default:
                return null;
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Input input = (Input) inputs.elementAt(rowIndex);
        try{
            switch (columnIndex) {
                case INDEX_COLUMN:
                    input.setIndex((String) aValue);
                    break;
                case LENGTH_COLUMN:
                     input.setLength((String) aValue);
                    break;
                case QUANTITY_COLUMN:
                    input.setQuantity((String) aValue);
                    break;
            }
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    public void addRow() {
        // index = index of Vector + 1 => last index = size of Vector + 1
        int index = inputs.size() + 1;
        inputs.addElement(new Input(index));
        fireTableRowsInserted(index - 1, index - 1);
    }

    public void removeRow() {
        int index = inputs.size() - 1;
        if(index >= 0) {
            inputs.removeElementAt(index);
            fireTableRowsDeleted(index, index);
        }
    }

    public void resetRows() {
        inputs.removeAllElements();
        addRow();
    }
}
