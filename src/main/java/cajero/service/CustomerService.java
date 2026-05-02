package cajero.service;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;

import java.util.List;

public interface CustomerService {

    // CRUD Customer
    Customer register(Customer customer);
    Customer findCustomer(int id);
    List<Customer> listCustomers();
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(int id);

    // Read/associate Accounts
    void addAccount(int customerId, Account account);
    List<Account> getAccountsByCustomer(int customerId);
    Account findAccountByNumber(String accountNumber);

    // Read/associate Cards
    void addCard(int customerId, Card card);
    List<Card> getCardsByCustomer(int customerId);
    Card findCardByNumber(String cardNumber);

    // Transactions
    boolean deposit(String accountNumber, double amount);

    boolean withdraw(String accountNumber, String cardNumber, String pin, double amount);
}
