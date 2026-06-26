import java.util.Scanner;


class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0.0;
        }
    }


    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Successfully deposited: $%.2f\n", amount);
        } else {
            System.out.println("Transaction Failed: Deposit amount must be positive.");
        }
    }

    
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Transaction Failed: Withdrawal amount must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Transaction Failed: Insufficient balance available.");
            return false;
        }
        
        balance -= amount;
        System.out.printf("Successfully withdrew: $%.2f\n", amount);
        return true;
    }
}


class ATM {
    private final BankAccount account; 
    private final Scanner scanner;

   
    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }


    public void start() {
        while (true) {
            System.out.println("\n=============================");
            System.out.println("        ATM INTERFACE        ");
            System.out.println("=============================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Exit Terminal");
            System.out.print("Select an option (1-4): ");

            int choice = readValidInt();

            switch (choice) {
                case 1:
                    System.out.printf("Current Balance: $%.2f\n", account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: $");
                    double depAmount = readValidDouble();
                    account.deposit(depAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: $");
                    double withAmount = readValidDouble();
                    account.withdraw(withAmount);
                    break;
                case 4:
                    System.out.println("Session ended. Please take your card. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option! Please enter a choice between 1 and 4.");
            }
        }
    }

    private int readValidInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid format. Please enter a choice number: ");
            }
        }
    }

    
    private double readValidDouble() {
        while (true) {
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0) {
                    System.out.print("Amount cannot be negative. Enter again: $");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid currency format. Enter a valid number: $");
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        
        BankAccount userAccount = new BankAccount(1000.00);

        
        ATM atmMachine = new ATM(userAccount);

  
        atmMachine.start();
    }
}
