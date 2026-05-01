package cajero.service;

import cajero.domain.Account;
import cajero.domain.Customer;
import cajero.repository.AccountRepository;
import cajero.repository.CustomerRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addAccount(int customerId, Account account) {
        if (account == null) return;
        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null) return;
        if (account.getOwner() == null) {
            account.setOwner(c);
        }
        accountRepository.addAccount(customerId, account);
    }

    @Override
    public List<Account> getAccountsByCustomer(int customerId) {
        return accountRepository.getAccountsByCustomer(customerId);
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        return accountRepository.findAccountByNumber(accountNumber);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    @Override
    public boolean deleteAccountByNumber(String accountNumber) {
        return accountRepository.deleteAccountByNumber(accountNumber);
    }
}
