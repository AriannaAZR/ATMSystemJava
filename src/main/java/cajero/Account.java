package cajero;

import java.util.Scanner;

public class Account {

    private static Scanner sc = new Scanner(System.in);

    private String number;
    private double balance;
    private Customer owner;
    private String accountType;


    public Account(){}

    public Account(String number, double balance, Customer owner, String accountType) {
        this.number = number;
        this.balance = balance;
        this.owner = owner;
        this.accountType = accountType;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public static Account createAccount(Account account, Customer customer) {
        System.out.println("---Creating account ---");

        System.out.println("Enter account number: ");
        account.setNumber(sc.nextLine());

        System.out.println("Enter account type (Savings/Current): ");
        account.setAccountType(sc.nextLine());

        System.out.println("Enter initial deposit amount: ");
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid deposit amount. Please enter a number: ");
            sc.next();
        }
        account.setBalance(sc.nextDouble());
        sc.nextLine();

        account.setOwner(customer);
        System.out.println("Account successfully linked to: " + customer.getName());
        return account;
    }

    public void getAccountDetails() {
        System.out.println("---Account details ---");
        System.out.println("Account number: " + this.number);
        System.out.println("Account type: " + this.accountType);
        System.out.println("Owner: "+ this.owner.getName());
        System.out.println("Current balance: $" + this.balance);
    }



}
