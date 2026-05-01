package cajero.repository;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;

import java.util.*;

/**
 * In-memory repository for Customers, their Accounts and Cards.
 */
public class CustomerRepository {

    private final Map<Integer, Customer> customers = new HashMap<>();
    private final Map<Integer, List<Account>> accountsByCustomer = new HashMap<>();
    private final Map<Integer, List<Card>> cardsByCustomer = new HashMap<>();

    // Customers
    public Customer saveCustomer(Customer customer) {
        if (customer == null) return null;
        customers.put(customer.getId(), customer);
        return customer;
    }

    public Customer findCustomerById(int id) {
        return customers.get(id);
    }

    public List<Customer> findAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    // Accounts
    public void addAccount(int customerId, Account account) {
        accountsByCustomer.computeIfAbsent(customerId, k -> new ArrayList<>()).add(account);
    }

    public List<Account> getAccountsByCustomer(int customerId) {
        return new ArrayList<>(accountsByCustomer.getOrDefault(customerId, Collections.emptyList()));
    }

    public Account findAccountByNumber(String accountNumber) {
        if (accountNumber == null) return null;
        for (List<Account> list : accountsByCustomer.values()) {
            for (Account acc : list) {
                if (accountNumber.equals(acc.getNumber())) {
                    return acc;
                }
            }
        }
        return null;
    }

    // Cards
    public void addCard(int customerId, Card card) {
        cardsByCustomer.computeIfAbsent(customerId, k -> new ArrayList<>()).add(card);
    }

    public List<Card> getCardsByCustomer(int customerId) {
        return new ArrayList<>(cardsByCustomer.getOrDefault(customerId, Collections.emptyList()));
    }

    public Card findCardByNumber(String cardNumber) {
        if (cardNumber == null) return null;
        for (List<Card> list : cardsByCustomer.values()) {
            for (Card card : list) {
                if (cardNumber.equals(card.getCardNumber())) {
                    return card;
                }
            }
        }
        return null;
    }
}
