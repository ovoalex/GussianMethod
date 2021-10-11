import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class Gaussian {

    public static void gaussianElimination(double [][] matrix) {
        int n = matrix.length;

        // Find the scale vector and store them in array scale.
        double [] scale = new double[n];
        for(int r=0; r<n; r++) {
            double max = 0;
            for(int c=0; c<n; c++) {
                if(Math.abs(matrix[r][c]) > max)
                    max = Math.abs(matrix[r][c]); 
            }
            scale[r] = max;
        }
        // Print out the scale vector
        System.out.print("\nThe scale vector: (");
        for(int s=0; s<n; s++)
            System.out.printf("%5.2f",scale[s]);
        System.out.printf(")%n%n");
        

        for(int i=0; i<n; i++) {

            //print the matrix
            for(int r=0; r<n; r++) {
                for(int c=0; c<n+1; c++) {
                    System.out.printf("%6.2f",matrix[r][c]);
                }
                System.out.printf("%n");
            }

            
            pivot(matrix, n, i, scale); // call pivot function to calculate scaled pivot and max

            // Eliminate each row 
            for(int row=i+1; row<n; row++) {
                double div = matrix[row][i]/matrix[i][i];
                for(int col=0; col<n+1; col++) {
                    matrix[row][col] = (matrix[row][col] - (matrix[i][col]*(div)));
                }
            }
        }

        // check for infinite solutions and see if there is no unique solution.
        try {
            if(matrix[n-1][n-1] == 0 && matrix[n-1][n] == 0)
                throw new Exception("There's no unique solution");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // checking to see if there is solutions or not
        try {
            if(matrix[n-1][n-1] == 0)
                throw new Exception("There's no solution");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // Find x1,x2,x3,... after eliminate.
        double [] result = new double[n];
        for(int i=n-1; i>=0; i--) {
            double add = 0;
            for(int j=i; j<n; j++) {
                add = add + (matrix[i][j] * result[j]);
                result[i] = (matrix[i][n] - add) / matrix[i][i];  
            }
        }

        // Print out the result
        System.out.println("The result is: ");
        for(int res=0; res<result.length; res++){
            System.out.printf("x%d = %5.2f%n",(res+1), result[res]);
        }
        System.out.printf("%n");
    };

    // Pivot function calculate the scaled ratio and swap the rows.
    // Print out the scaled ratio and the selected row.
    public static void pivot(double [][]matrix, int n, int i, double [] scale) {
        double max = -1e100;
        int maxRow = 0;

        System.out.print("Scaled ratio: (");
        for(int coefficient=i; coefficient < n; coefficient++) {

            //print out the ratio
            System.out.printf("%5.2f",Math.abs(matrix[coefficient][i]/scale[coefficient])); // Print each scaled value

            if(max < Math.abs(matrix[coefficient][i]/scale[coefficient])) {
                maxRow = coefficient;
                max = Math.abs(matrix[coefficient][i]/scale[coefficient]);
            }
        }
        int rowSelected = maxRow + 1;
        System.out.println(" )");
        System.out.println("Row " + rowSelected + " is selected as the pivot row."); // Print the selected row
        System.out.println("\n");

        // Swap the scaled pivot array
        double temp = scale[i];
        scale[i] = scale[maxRow];
        scale[maxRow] = temp;
        // Swap the row of the matrix
        double [] temp1 = matrix[i];
        matrix[i] = matrix[maxRow];
        matrix[maxRow] = temp1;
        
    }
    
    // Function to enter matrix
    private static double [][] enterMatrix() {
        Scanner sc = new Scanner(System.in);
        int row = 2;

        try {
            System.out.print("Enter the number of equations: ");
            row = sc.nextInt();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Enter the invalid value.");
            System.exit(0);
        }

        double [][] matrix = new double [row][row+1]; // declare matrix

        int option = 1;
        
        try {
            System.out.println("The Gaussian elimination with Scaled Partial Pivoting method\n\t1 - Enter coefficient equations.\n\t2 - Enter a file name.");
            option = sc.nextInt();
            while (option < 1 || option > 2) {
                System.out.print("Enter 1 or 2 again to choose options: ");
                option = sc.nextInt();
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Enter the invalid value.");
            System.exit(0);
        }

        if (option == 1) { //import equations from console
            try {
                System.out.println("Enter equation by row: ");

                for (int r=0; r<row; r++) {
                    for (int c=0; c<row+1; c++) {
                        matrix[r][c] = sc.nextInt();
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Invalid value.");
                System.exit(0);
            }
        }

        
        if (option == 2) { // import equations from text file
            
            System.out.print("Enter the file name: ");
            Scanner read = new Scanner("");
            try {
                File file = new File(sc.next());

                while (!file.exists()) {
                    System.out.print("Enter the file name again: ");
                    file = new File(sc.next());
                }

                read = new Scanner(file);
                for(int r = 0; r < row; r++) {
                    for(int c = 0; c < row+1; c++) {
                        matrix[r][c] = read.nextDouble();
                    }
                }
            } 
            catch (Exception e) {
                System.out.println("Error, the file is not found or invalid values.");
                System.exit(0);
            }
            read.close();
        }
        return matrix;
    };


    
    public static void main(String args [] ) {
        
        double [][]m = enterMatrix(); // Enter the number of equations and input the coefficients.
        
        gaussianElimination(m);

    }
}