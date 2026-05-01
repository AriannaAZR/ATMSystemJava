package cajero.service;

import cajero.domain.Account;

import java.util.List;

public interface AccountService {
    void addAccount(int customerId, Account account);
    List<Account> getAccountsByCustomer(int customerId);
    Account findAccountByNumber(String accountNumber);
    boolean updateAccount(Account account);
    boolean deleteAccountByNumber(String accountNumber);
}
