package org.example.domain;
import org.example.enums.AccountType;

public abstract class BankAccount {
    protected long balance;

    public BankAccount(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public abstract AccountType getAccountType();
}
