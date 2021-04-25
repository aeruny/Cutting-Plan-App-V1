import java.awt.*;
import java.io.*;
import java.nio.Buffer;

public class Printer {


    public static void printOutput(double target, Object[][] inputs, double total, double remainder, Object[][] cuttingPlan, String[] columnNames) throws IOException {
        File file = new File("Cutting Plan.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        // Introduction
        bw.append("Cutting Plan,,Created By Mingeon Sung\n");
        bw.append("\n\n");

        // User Inputs
        bw.append("User Input:\n");
        bw.append("Target, Length:," + target + "\n");
        bw.append("#,Length,Quantity\n");
        for(int i = 0; i < inputs.length; i++) {
            bw.append((i + 1) +"," + inputs[i][0] + "," + inputs[i][1] + "\n");
        }
        bw.append("\n\n");

        // Results
        bw.append("Results:,\n");
        bw.append("Total:," + total + "\n");
        bw.append("Total Excess Length:," + remainder + "\n");
        bw.append("\n\n");


        // Cutting Plan
        bw.append("Cutting Plan:\n");
        String str = "";
        for(int i = 0; i < columnNames.length; i++) {
            str += columnNames[i] + ",";
        }
        bw.append(str + "\n");
        String str2 = "";
        for(int i = 0; i < cuttingPlan.length; i++) {
            str2 = "";
            for(int j = 0; j < cuttingPlan[i].length; j++) {
                str2 += cuttingPlan[i][j] + ",";
            }
            bw.append(str2 + "\n");
        }
        open(file);
        bw.flush();
        bw.close();
        fw.close();
    }

    public static boolean open(File file)
    {
        try
        {
            if (OSDetector.isWindows())
            {
                Runtime.getRuntime().exec(new String[]
                        {"rundll32", "url.dll,FileProtocolHandler",
                                file.getAbsolutePath()});
                return true;
            } else if (OSDetector.isLinux() || OSDetector.isMac())
            {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
                        file.getAbsolutePath()});
                return true;
            } else
            {
                // Unknown OS, try with desktop
                if (Desktop.isDesktopSupported())
                {
                    Desktop.getDesktop().open(file);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public Printer() throws IOException {


        /*
        file = new File("Cutting Plan.txt");
        if(!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("===========================================================\n");
        bw.write("||                                                       ||\n");
        bw.write("||             Cutting Plan by Mingeon Sung              ||\n");
        bw.write("||                                                       ||\n");
        bw.write("===========================================================\n");

        // User Input
        bw.write("User Input:\n");
        double[][] inputs = new double[10][2];
        for(int i = 0; i < 10; i++) {
            bw.write("Material #" + i + " :\t");
            bw.write(inputs[i][0] + "\t" + inputs[i][1] + "\n");
        }
        bw.write("\n\n");

        // Result
        bw.write("Result:\n");
        bw.write("Total Number of Raw Material:\t");

        bw.write("Total Length of Excess:\t");
        bw.write("\n\n");

        // Cutting Plan
        bw.write("Cutting Plan:\n");
        Object[][] cuttingPlan = new Object[10][6];
        for(int i = 0; i < cuttingPlan.length; i++) {
            //lines2[i] = i + "\t";
            for(int j = 0; j < cuttingPlan[i].length; j++) {
                bw.write(cuttingPlan[i][j] + "\t");
            }
            bw.write("\n");
        }
        bw.write("\n\n");
        bw.close();
        fw.close();
         */
    }
}
