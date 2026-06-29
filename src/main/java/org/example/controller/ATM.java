package org.example.controller;

import org.example.domain.BankAccount;
import org.example.domain.User;
import org.example.enums.AccountType;
import org.example.enums.Denomination;
import org.example.service.AccountService;
import org.example.service.UserService;

import java.util.Scanner;

public class ATM {
    private UserService userService;
    private AccountService accountService;
    private User currentUser;
    private Scanner scanner;

    public ATM(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                showAuthMenu(); // Приложение начинает работу с авторизации 
            } else {
                showMainMenu();
            }
        }
    }

    private void showAuthMenu() {
        System.out.println("1. Sign in (login)");
        System.out.println("2. Sign up (register)");
        System.out.print("> ");
        
        if (!scanner.hasNextInt()) {
            scanner.next();
            return;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        try {
            if (choice == 1) {
                System.out.print("Login: ");
                String login = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                currentUser = userService.login(login, password);
                System.out.println("Успешный вход!");
            } else if (choice == 2) {
                System.out.print("Login: ");
                String login = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                currentUser = userService.register(login, password);
                System.out.println("Успешная регистрация!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage()); // Обработка исключений оркестратором 
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Check balance");
        System.out.println("2. Open account");
        System.out.println("3. Deposit money");
        System.out.println("4. Withdraw money");
        System.out.println("5. Logout");
        System.out.print("> ");

        if (!scanner.hasNextInt()) {
            scanner.next();
            return;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            if (choice == 1) {
                long total = accountService.getTotalBalance(currentUser.getAccounts());
                System.out.println("Общий баланс: " + total);
                for (int i = 0; i < currentUser.getAccounts().size(); i++) {
                    BankAccount acc = currentUser.getAccounts().get(i);
                    System.out.println("Счет " + i + " (" + acc.getAccountType() + "): " + acc.getBalance());
                }
            } else if (choice == 2) {
                System.out.println("Какой счет открыть? 1 - Debit, 2 - Credit");
                int typeChoice = scanner.nextInt();
                if (typeChoice == 1) {
                    accountService.openAccount(currentUser, AccountType.DEBIT);
                    System.out.println("Дебетовый счет открыт.");
                } else if (typeChoice == 2) {
                    accountService.openAccount(currentUser, AccountType.CREDIT);
                    System.out.println("Кредитный счет открыт.");
                }
            } else if (choice == 3) {
                BankAccount acc = selectAccount();
                if (acc != null) {
                    System.out.print("Сумма пополнения: ");
                    long amount = scanner.nextLong();
                    accountService.deposit(acc, amount);
                    System.out.println("Успешно!");
                }
            } else if (choice == 4) {
                BankAccount acc = selectAccount();
                if (acc != null) {
                    System.out.print("Сумма снятия: ");
                    long amount = scanner.nextLong();
                    accountService.withdraw(acc, amount);
                    dispenseMoney(amount);
                }
            } else if (choice == 5) {
                currentUser = null;
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private BankAccount selectAccount() {
        if (currentUser.getAccounts().isEmpty()) {
            System.out.println("У вас нет открытых счетов!");
            return null;
        }
        System.out.println("Выберите счет:");
        for (int i = 0; i < currentUser.getAccounts().size(); i++) {
            BankAccount acc = currentUser.getAccounts().get(i);
            System.out.println(i + ". " + acc.getAccountType() + " (Баланс: " + acc.getBalance() + ")");
        }
        System.out.print("> ");
        int index = scanner.nextInt();
        if (index >= 0 && index < currentUser.getAccounts().size()) {
            return currentUser.getAccounts().get(index);
        }
        System.out.println("Неверный номер счета.");
        return null;
    }

    private void dispenseMoney(long amount) {
        long leftToPay = amount;
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
