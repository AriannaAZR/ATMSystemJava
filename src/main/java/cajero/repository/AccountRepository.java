package cajero.repository;

import cajero.domain.Account;

import java.util.List;

public class AccountRepository {

    private final CustomerRepository customerRepository;

    public AccountRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addAccount(int customerId, Account account) {
        customerRepository.addAccount(customerId, account);
    }

    public List<Account> getAccountsByCustomer(int customerId) {
        return customerRepository.getAccountsByCustomer(customerId);
    }

    public Account findAccountByNumber(String accountNumber) {
        return customerRepository.findAccountByNumber(accountNumber);
    }

    public boolean updateAccount(Account updated) {
        return customerRepository.updateAccount(updated);
    }

    public boolean deleteAccountByNumber(String accountNumber) {
        return customerRepository.deleteAccountByNumber(accountNumber);
    }
}
