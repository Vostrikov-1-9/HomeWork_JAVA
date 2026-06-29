package org.example.domain;
import org.example.enums.AccountType;

public class DebitAccount extends BankAccount {
    public DebitAccount() {
        super(0); // Дебетовый счет создается с балансом = 0 
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.DEBIT;
    }
}
