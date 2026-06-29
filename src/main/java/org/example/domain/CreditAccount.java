package org.example.domain;
import org.example.enums.AccountType;

public class CreditAccount extends BankAccount {
    private long creditLine;

    public CreditAccount() {
        super(20000); // Кредитный счет создается с creditLine = 20000 
        this.creditLine = 20000;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CREDIT;
    }
}
