public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Иван Иванов", 1000);
        ATM atm = new ATM(account);
        atm.start();
    }
}
