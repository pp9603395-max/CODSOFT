import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("    STUDENT GRADE CALCULATOR     ");
        System.out.println("=================================");

       
        System.out.print("Enter the number of subjects: ");
        int numSubjects = readValidInt(scanner, 1, 20); 

        double[] marks = new double[numSubjects];
        double totalMarks = 0;

        System.out.println("\nEnter marks obtained out of 100 for each subject:");
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Subject " + (i + 1) + ": ");
            marks[i] = readValidMarks(scanner);
            
            
            totalMarks += marks[i];
        }

  
        double averagePercentage = totalMarks / numSubjects;


        String grade;
        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F (Fail)";
        }

       
        System.out.println("\n=================================");
        System.out.println("         FINAL RESULTS           ");
        System.out.println("=================================");
        System.out.printf("Total Marks Obtained : %.2f / %d\n", totalMarks, (numSubjects * 100));
        System.out.printf("Average Percentage   : %.2f%%\n", averagePercentage);
        System.out.println("Assigned Letter Grade: " + grade);
        System.out.println("=================================");

        scanner.close();
    }

    
    private static int readValidInt(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val < min || val > max) {
                    System.out.print("Please enter a realistic number of subjects (" + min + "-" + max + "): ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid format. Please enter a valid whole number: ");
            }
        }
    }

    
    private static double readValidMarks(Scanner scanner) {
        while (true) {
            try {
                double marks = Double.parseDouble(scanner.nextLine().trim());
                if (marks < 0 || marks > 100) {
                    System.out.print("Invalid score. Marks must be between 0 and 100: ");
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.print("Invalid format. Enter a numerical value (0-100): ");
            }
        }
    }
}
