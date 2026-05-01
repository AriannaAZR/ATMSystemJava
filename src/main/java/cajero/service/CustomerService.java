package cajero.service;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;

import java.util.List;

public interface CustomerService {


    Customer register(Customer customer);

    Customer findCustomer(int id);

    List<Customer> listCustomers();


    void addAccount(int customerId, Account account);

    List<Account> getAccountsByCustomer(int customerId);

    Account findAccountByNumber(String accountNumber);


    void addCard(int customerId, Card card);

    List<Card> getCardsByCustomer(int customerId);

    Card findCardByNumber(String cardNumber);


    boolean deposit(String accountNumber, double amount);

    boolean withdraw(String accountNumber, String cardNumber, String pin, double amount);
}
