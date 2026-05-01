package cajero.service;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;

import java.util.List;

public interface CustomerService {

    // Customers
    Customer register(Customer customer);

    Customer findCustomer(int id);

    List<Customer> listCustomers();

    // Accounts
    Account openAccountInteractive(int customerId);

    List<Account> getAccountsByCustomer(int customerId);

    Account findAccountByNumber(String accountNumber);

    // Cards
    Card issueCardInteractive(int customerId);

    List<Card> getCardsByCustomer(int customerId);

    Card findCardByNumber(String cardNumber);
}
