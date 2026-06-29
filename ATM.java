import java.util.Scanner;

public class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            showMenu();
            System.out.print("> ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.print("How much money to deposit?\n> ");
                    long depositAmount = scanner.nextLong();
                    if (depositAmount > 0) {
                        account.deposit(depositAmount);
                        System.out.println("Success!");
                    } else {
                        System.out.println("Amount must be greater than 0");
                    }
                    break;
                case 3:
                    System.out.print("How much money to withdraw?\n> ");
                    long withdrawAmount = scanner.nextLong();
                    
                    if (withdrawAmount > 0) {
                        if (account.withdraw(withdrawAmount)) {
                            dispenseMoney(withdrawAmount);
                        } else {
                            System.out.println("Not enough money");
                        }
                    } else {
                        System.out.println("Amount must be greater than 0");
                    }
                    break;
                case 4:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
            System.out.println();
        }
    }

    private void showMenu() {
        System.out.println("1. Check balance");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Exit");
    }

    private void dispenseMoney(long amount) {
        long remaining = amount;
        for (Denomination denomination : Denomination.values()) {
            int denomValue = denomination.getValue();
            if (remaining >= denomValue) {
                long count = remaining / denomValue;
                System.out.println(denomValue + " x " + count);
                remaining %= denomValue;
            }
        }
    }
}
