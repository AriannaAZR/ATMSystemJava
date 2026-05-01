package cajero.domain;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Transaction {
    private static Scanner sc = new Scanner(System.in);

    private int id;
    private String transactionType;
    private double amount;
    private LocalDateTime date;

    public Transaction() {

    }

    public Transaction(int id, String transactionType, double amount, LocalDateTime date) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public static Transaction createTransaction(Account account, Card card, String transactionType) {
        Transaction t = new Transaction();
        t.setTransactionType(transactionType);

        System.out.println("---Transaction" + transactionType + "---");
        System.out.println("Enter amount: ");

        while (!sc.hasNextDouble()) {
            System.out.println("Invalid amount.");
            sc.next();
        }
        double amount = sc.nextDouble();
        sc.nextLine();

        if (transactionType.equalsIgnoreCase("Withdrawal")) {
            System.out.println("Enter your 4-digit PIN to authorize: ");
            String pin = sc.nextLine();
            t.executeWithdrawal(account, card, pin, amount);
        } else if (transactionType.equalsIgnoreCase("Deposit")) {
            t.executeDeposit(account, amount);

        }

        return t;
    }

    public void executeWithdrawal(Account account, Card card, String pin, double amount) {
        if (card.isBlocked()) {
            System.out.println("Error: Card is blocked.");
            return;
        }
        if (!card.getEncryptedPin().equals(pin)) {
            System.out.println("Error: Invalid PIN.");
            return;
        }
        if (amount > account.getBalance()) {
            System.out.println("Error: Insufficient funds. Balance: $" +  account.getBalance());
            return;
        }
        account.setBalance(account.getBalance() - amount);
        this.amount = amount;
        this.date = LocalDateTime.now();
        System.out.println("---Withdrawal Successful---");
        System.out.println("New Balance: " + account.getBalance());
    }

    public void executeDeposit(Account account, double amount) {
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive.");
            return;
        }

        account.setBalance(account.getBalance() + amount);
        this.amount = amount;
        this.date = LocalDateTime.now();
        System.out.println("=== Deposit Successful ===");
        System.out.println("New Balance: $" + account.getBalance());
    }
}
