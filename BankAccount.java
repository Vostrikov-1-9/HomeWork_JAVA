public class BankAccount {
    private String ownerName;
    private long balance;

    public BankAccount(String ownerName, long balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public void deposit(long amount) {
        if (amount > 0) {
            this.balance += amount; // используем this для наглядности
        }
    }

    public boolean withdraw(long amount) {
        // Ранний выход, если сумма кривая
        if (amount <= 0) {
            return false;
        }

        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public long getBalance() {
        return balance;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
}
