
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CuttingPlan {

    public static double[] toLengths(Object[][] objects) {
        double[] lengths = new double[objects.length];
        for(int i = 0; i < objects.length; i++) {
            String str = (String) objects[i][0];
            lengths[i] = Double.parseDouble(str);
        }
        return lengths;
    }
    public static int[] toQuantities(Object[][] objects) {
        int[] quantities = new int[objects.length];
        for(int i = 0; i < objects.length; i++) {
            String str = (String) objects[i][1];
            quantities[i] = Integer.parseInt(str);
        }
        return quantities;
    }

    public static double[][] getCuttingPlan(double target, Object[][] objects) {
        double[][] lists = sortLists(target, toLengths(objects), toQuantities(objects));
        double[] lengths = Arrays.copyOf(lists[0], toLengths(objects).length);
        int[] quantities = new int[toQuantities(objects).length];

        for(int i = 0; i < toQuantities(objects).length; i++) {
            quantities[i] = (int) lists[1][i];
        }

        int a = 0;                                      // index/pointer of quantities[]
        int b = 0;                                      // index/pointer of lengths[]
        int c = 0;                                      // index of plan ArrayList (row)
        int d = 0;                                      // index of plan ArrayList[] (col)
        double temp = target;                           // temporary value for calculation

        int planLength = (int) (target / lengths[lengths.length-1]);
        List<double[]> cuttingPlan = new ArrayList<double[]>();

        cuttingPlan.add(new double[planLength]);
        while(a < lengths.length) {
            // When the quantity at index a is 0, the corresponding length has been exhausted
            // Increment the indices a and b
            if(quantities[a] == 0) {
                a++;
                b = a;
            }
            else {
                // If the current value of temp is subtractable by the pointed length,
                // Subtract temp by the length and record the length to cuttingPlan
                // Else, move the pointer b to the next length
                if(temp >= lengths[b] && quantities[b] > 0) {
                    //System.out.println("a=" + a + " b= " + b + "      " + quantities[b]);
                    temp -= lengths[b];
                    quantities[b]--;
                    cuttingPlan.get(c)[d] = lengths[b];
                    d++;
                } else {
                    b++;
                }
            }

            // If the current value of temp is not subtractable at all,
            // Reset the temp value to the target value and pointer b to pointer a
            // To record the next row of plan ArrayList[], add a new element
            // And set pointer c to the new row, and set pointer d to 0;
            if(a < lengths.length && temp < getMin(a, lengths, quantities)) {
                cuttingPlan.add(new double[planLength]);
                c++;
                d = 0;
                temp = target;
                b = a;
            }
        }
        return optimizeCuttingPlan(target, cuttingPlan);
    }

    private static double getMin(int index, double[] lengths, int[] quantities) {
        double min = lengths[index];

        for(int i = index; i < lengths.length; i++) {
            if(quantities[i] > 0 && lengths[i] < min) {
                min = lengths[i];
            }
        }
        return min;
    }


    // This method reduces the length of the array to its maximum length and converts
    // the data type into a 2D array for the integration into JTable.
    private static double[][] optimizeCuttingPlan(double target, List<double[]> cuttingPlan) {
        // Check if the last row is empty or not
        boolean isEmpty = true;
        for(int i = 0; i < cuttingPlan.get(cuttingPlan.size() - 1).length; i++) {
            if(cuttingPlan.get(cuttingPlan.size() - 1)[i] != 0)
                isEmpty = false;
        }
        if(isEmpty)
            cuttingPlan.remove(cuttingPlan.size() - 1);

        boolean key = true;
        int trueLength = cuttingPlan.get(0).length;
        for(int i = 0; i < trueLength; i++) {
            for(int j = 0; j < cuttingPlan.size() && key; j++) {
                if(cuttingPlan.get(j)[i] != 0)
                    key = false;
            }
            if(key)
                trueLength = i;
            key = true;
        }
        return addOrder(target, copyList(toArray(cuttingPlan), trueLength));
    }


    //This class adds the order (ex. #1, #2, #3) at the first column of each row and
    // adds the remainders at the last column of each row.
    private static double[][] addOrder(double target, double[][] original) {
        double[][] copy = new double[original.length][original[0].length+2];
        double sum = 0;
        for(int i = 0; i < original.length; i++) {
            copy[i][0] = i + 1;
            sum = 0;
            for(int j = 0; j < original[i].length; j++) {
                copy[i][j + 1] = original[i][j];
                sum += original[i][j];
            }
            copy[i][original[i].length + 1] = target - sum;
        }
        return copy;
    }

    private static double[][] copyList(double[][] original, int length) {
        double[][] copy = new double[original.length][];
        for(int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], length);
        }
        return copy;
    }

    private static double[][] toArray(List<double[]> original) {
        double[][] array = new double[original.size()][];
        for(int i = 0;  i < original.size(); i++) {
            array[i] = original.get(i);
        }
        return array;
    }


    private static double[][] sortLists(double target, double[] lengths, int[] quantities) {
        double max;
        int temp;
        int index;
        double[][] lists = new double[2][lengths.length];
        for(int i = 0; i < lengths.length; i++) {
            max = lengths[i];
            index = i;
            if(target < lengths[i])
                return null;
            for(int j = i; j < lengths.length; j++) {
                if(lengths[j] > max) {
                    index = j;
                    max = lengths[index];
                }
            }
            lengths[index] = lengths[i];
            lengths[i] = max;
            temp = quantities[index];
            quantities[index] = quantities[i];
            quantities[i] = temp;

            lists[0][i] = lengths[i];
            lists[1][i] = quantities[i];
        }
        return lists;
    }

}
