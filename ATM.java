import java.util.Scanner;

public class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void showMenu() {
        System.out.println("1. Check balance");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Exit");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            showMenu();
            System.out.print("> ");
            
            // Защита от ввода букв вместо цифр
            if (!scanner.hasNextInt()) {
                System.out.println("Error: enter a number!");
                scanner.next(); // чистим буфер
                continue;
            }
            
            int action = scanner.nextInt();

            if (action == 1) {
                System.out.println("Your balance: " + account.getBalance());
                
            } else if (action == 2) {
                System.out.println("How much money to deposit?");
                System.out.print("> ");
                long money = scanner.nextLong();
                
                if (money > 0) {
                    account.deposit(money);
                    System.out.println("Success!");
                } else {
                    System.out.println("Amount must be greater than 0");
                }
                
            } else if (action == 3) {
                System.out.println("How much money to withdraw?");
                System.out.print("> ");
                long money = scanner.nextLong();
                
                if (money > 0) {
                    boolean success = account.withdraw(money);
                    if (success) {
                        dispenseMoney(money);
                    } else {
                        System.out.println("Not enough money");
                    }
                } else {
                    System.out.println("Amount must be greater than 0");
                }
                
            } else if (action == 4) {
                System.out.println("Goodbye!");
                break; // выходим из бесконечного цикла
                
            } else {
                System.out.println("Wrong command, try again");
            }
            
            System.out.println(); // пустая строка в конце цикла
        }
    }

    private void dispenseMoney(long amount) {
        long leftToPay = amount; // сколько осталось выдать
        
        for (Denomination d : Denomination.values()) {
            int nominal = d.getValue();
            
            if (leftToPay >= nominal) {
                long count = leftToPay / nominal;
                System.out.println(nominal + " x " + count);
                leftToPay = leftToPay % nominal;
            }
        }
    }
}
