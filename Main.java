public class Main {
    public static void main(String[] args) {
        BankAccount myAccount = new BankAccount("Test User", 5000);
        ATM atm = new ATM(myAccount);
        
        atm.start();
    }
}
