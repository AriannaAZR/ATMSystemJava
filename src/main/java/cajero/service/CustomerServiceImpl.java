package cajero.service;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;
import cajero.repository.CustomerRepository;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    // Customers CRUD
    @Override
    public Customer register(Customer customer) {
        return repository.saveCustomer(customer);
    }

    @Override
    public Customer findCustomer(int id) {
        return repository.findCustomerById(id);
    }

    @Override
    public List<Customer> listCustomers() {
        return repository.findAllCustomers();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return repository.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int id) {
        return repository.deleteCustomer(id);
    }

    // Accounts
    @Override
    public void addAccount(int customerId, Account account) {
        Customer customer = repository.findCustomerById(customerId);
        if (customer == null || account == null) return;
        account.setOwner(customer);
        repository.addAccount(customerId, account);
    }

    @Override
    public List<Account> getAccountsByCustomer(int customerId) {
        return repository.getAccountsByCustomer(customerId);
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        return repository.findAccountByNumber(accountNumber);
    }

    // Cards
    @Override
    public void addCard(int customerId, Card card) {
        Customer customer = repository.findCustomerById(customerId);
        if (customer == null || card == null) return;
        if (card.getCardName() == null || card.getCardName().isBlank()) {
            card.setCardName(customer.getName());
        }
        repository.addCard(customerId, card);
    }

    @Override
    public List<Card> getCardsByCustomer(int customerId) {
        return repository.getCardsByCustomer(customerId);
    }

    @Override
    public Card findCardByNumber(String cardNumber) {
        return repository.findCardByNumber(cardNumber);
    }

    // Transactions
    @Override
    public boolean deposit(String accountNumber, double amount) {
        Account acc = repository.findAccountByNumber(accountNumber);
        if (acc == null || amount <= 0) return false;
        acc.setBalance(acc.getBalance() + amount);
        return true;
    }

    @Override
    public boolean withdraw(String accountNumber, String cardNumber, String pin, double amount) {
        Account acc = repository.findAccountByNumber(accountNumber);
        Card card = repository.findCardByNumber(cardNumber);
        if (acc == null || card == null) return false;
        if (card.isBlocked()) return false;
        if (!String.valueOf(card.getEncryptedPin()).equals(pin)) return false;
        if (amount <= 0 || acc.getBalance() < amount) return false;
        acc.setBalance(acc.getBalance() - amount);
        return true;
    }
}
