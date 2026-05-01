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

    // Customers
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

    // Accounts
    @Override
    public Account openAccountInteractive(int customerId) {
        Customer customer = repository.findCustomerById(customerId);
        if (customer == null) return null;
        Account account = cajero.domain.Account.createAccount(new Account(), customer);
        repository.addAccount(customerId, account);
        return account;
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
    public Card issueCardInteractive(int customerId) {
        Customer customer = repository.findCustomerById(customerId);
        if (customer == null) return null;
        // Se crea una tarjeta "vacía" y el método interactivo rellena los datos
        Card card = cajero.domain.Card.isueCard(new Card(0, "", "", ""), customer);
        repository.addCard(customerId, card);
        return card;
    }

    @Override
    public List<Card> getCardsByCustomer(int customerId) {
        return repository.getCardsByCustomer(customerId);
    }

    @Override
    public Card findCardByNumber(String cardNumber) {
        return repository.findCardByNumber(cardNumber);
    }
}
