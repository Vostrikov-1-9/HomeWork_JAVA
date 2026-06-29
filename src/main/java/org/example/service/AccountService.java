package org.example.service;

import org.example.domain.BankAccount;
import org.example.domain.CreditAccount;
import org.example.domain.DebitAccount;
import org.example.domain.User;
import org.example.enums.AccountType;
import org.example.exception.NotEnoughMoneyException;
import java.util.List;

public class AccountService {

    public void openAccount(User user, AccountType type) {
        if (type == AccountType.DEBIT) {
            user.getAccounts().add(new DebitAccount());
        } else if (type == AccountType.CREDIT) {
            user.getAccounts().add(new CreditAccount());
        }
    }

    public void deposit(BankAccount account, long amount) {
        if (amount > 0) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    public void withdraw(BankAccount account, long amount) {
        if (amount > 0) {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
            } else {
                throw new NotEnoughMoneyException("Недостаточно средств на счете"); // Валидация посредством исключений 
            }
        }
    }

    // Параметризованный метод по правилу PECS 
    public long getTotalBalance(List<? extends BankAccount> accounts) {
        long total = 0;
        for (BankAccount acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }
}
