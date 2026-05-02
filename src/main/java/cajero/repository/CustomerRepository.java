package cajero.repository;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;

import java.util.*;

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

    public Customer updateCustomer(Customer updated) {
        if (updated == null) return null;
        Customer existing = customers.get(updated.getId());
        if (existing == null) return null;
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setUser(updated.getUser());
        existing.setPassword(updated.getPassword());
        return existing;
    }

    public boolean deleteCustomer(int id) {
        Customer removed = customers.remove(id);
        accountsByCustomer.remove(id);
        cardsByCustomer.remove(id);
        return removed != null;
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

    public boolean updateAccount(Account updated) {
        if (updated == null || updated.getNumber() == null) return false;
        Account existing = findAccountByNumber(updated.getNumber());
        if (existing == null) return false;
        existing.setAccountType(updated.getAccountType());
        existing.setBalance(updated.getBalance());
        if (updated.getOwner() != null) {
            existing.setOwner(updated.getOwner());
        }
        return true;
    }

    public boolean deleteAccountByNumber(String accountNumber) {
        if (accountNumber == null) return false;
        for (Map.Entry<Integer, List<Account>> e : accountsByCustomer.entrySet()) {
            Iterator<Account> it = e.getValue().iterator();
            while (it.hasNext()) {
                Account a = it.next();
                if (accountNumber.equals(a.getNumber())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
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

    public boolean updateCard(Card updated) {
        if (updated == null || updated.getCardNumber() == null) return false;
        Card existing = findCardByNumber(updated.getCardNumber());
        if (existing == null) return false;
        existing.setEncryptedPin(updated.getEncryptedPin());
        existing.setBlocked(updated.isBlocked());
        existing.setExpirationDate(updated.getExpirationDate());
        if (updated.getCardName() != null && !updated.getCardName().isBlank()) {
            existing.setCardName(updated.getCardName());
        }
        return true;
    }

    public boolean deleteCardByNumber(String cardNumber) {
        if (cardNumber == null) return false;
        for (Map.Entry<Integer, List<Card>> e : cardsByCustomer.entrySet()) {
            Iterator<Card> it = e.getValue().iterator();
            while (it.hasNext()) {
                Card c = it.next();
                if (cardNumber.equals(c.getCardNumber())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
}
