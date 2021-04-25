# Cutting-Plan-App-V1
A cutting plan application programmed in Java Swing API.

This is one of my major desktop applications developed with Java Swing.
This application was requested by my father who is a mechanical design engineer.

The purpose of this application is to create a cutting plan for a raw material (like a metal bar).


**How to run the Cutting Plan App**
1. run the Launcher class.


**Instruction**

1. Enter the Length of the Raw Material and Insert the demanding lengths and quantities into the table. 
   ("Add" button adds a new row)("Delete" button removes the bottom-most row)
2. Click the "Generate" button to create the cutting plan.
3. Access the cutting plan through the table and Create a CSV file by clicking the "Excel" button.


**Warnings**

-Do not move file contents - this may cause the program to malfunction.

-Relocate or rename the csv file from the folder after generating the file because creating a new file will overwrite the existing contents.

-Table editor saves its contents once the cursor is moved or the "Enter" key is pressed. Be sure to enter the text to prevent the last-edited value from being omitted.


***Key Algorithm (getCuttingPlan(double target, Object[][] objects))***
getCuttingPlan method is located inside the CuttingPlan class.

This method generates the cutting plan with a recursion-like algorithm. It contains a loop that iterates based on four different indices.
Indices "a" and "b" represent the demanded lengths and quantities, and the indices "c" and "d" represent the indices for the ArrayList cuttingPlan that contains a double array (basically a 2D array).
Once the iteration completes, the cuttingPlan (ArrayList<double[]>) variable is optimized with optimizeCuttingPlan() method, which trims any empty array and converts the arrayList into a 2d array.

**What I learned from this project**

-Java Swing Table and TableModel interfaces

-Java AWT Layouts

-The concepts of Java's Object classes and the class hierarchy of Number class

-How to optimize code in a OOP programming

Developing the Algorithm was fairly easy compared to learning the nuances and the features of Java Swing. I feel more confident with Java in general.



There is a second version of the application updated with improvements proposed by my father and a few optimizations.
