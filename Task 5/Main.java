import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRollNumber() { return rollNumber; }
    public void setRollNumber(int rollNumber) { this.rollNumber = rollNumber; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Roll No: " + rollNumber + " | Name: " + name + " | Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_PATH = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile(); 
    }

    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
        System.out.println("Student added successfully!");
    }

    public boolean removeStudent(int rollNumber) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            students.remove(student);
            saveToFile();
            return true;
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNumber) {
                return s;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data. Starting with an empty database.");
        }
    }
}

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] Harris) {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM     ");
            System.out.println("=================================");
            System.out.println("1. Add a New Student");
            System.out.println("2. Edit Existing Student Info");
            System.out.println("3. Search for a Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Remove a Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            int choice = readValidInt();

            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    editStudentInfo();
                    break;
                case 3:
                    searchStudentInfo();
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    removeStudentInfo();
                    break;
                case 6:
                    System.out.println("Exiting Application. Data Saved. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose between 1 and 6.");
            }
        }
    }

    private static void addNewStudent() {
        String name = readValidString("Enter Student Name: ");
        System.out.print("Enter Roll Number: ");
        int roll = readValidInt();

        if (sms.searchStudent(roll) != null) {
            System.out.println("Error: A student with Roll Number " + roll + " already exists!");
            return;
        }

        String grade = readValidString("Enter Grade: ");
        sms.addStudent(new Student(name, roll, grade));
    }

    private static void editStudentInfo() {
        System.out.print("Enter Roll Number of the student to edit: ");
        int roll = readValidInt();
        Student student = sms.searchStudent(roll);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current Details: " + student);
        String newName = readValidString("Enter New Name (or press Enter to keep current): ");
        if (!newName.isEmpty()) {
            student.setName(newName);
        }

        String newGrade = readValidString("Enter New Grade (or press Enter to keep current): ");
        if (!newGrade.isEmpty()) {
            student.setGrade(newGrade);
        }

        sms.saveToFile();
        System.out.println("Student information updated successfully!");
    }

    private static void searchStudentInfo() {
        System.out.print("Enter Roll Number to search: ");
        int roll = readValidInt();
        Student student = sms.searchStudent(roll);

        if (student != null) {
            System.out.println("\nStudent Found: \n" + student);
        } else {
            System.out.println("Student with Roll Number " + roll + " not found.");
        }
    }

    private static void removeStudentInfo() {
        System.out.print("Enter Roll Number to remove: ");
        int roll = readValidInt();
        if (sms.removeStudent(roll)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static String readValidString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (prompt.contains("keep current") || !input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Field cannot be empty. Please try again.");
        }
    }

    private static int readValidInt() {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val < 0) {
                    System.out.print("Value cannot be negative. Enter again: ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input format. Please enter a valid number: ");
            }
        }
    }
}
