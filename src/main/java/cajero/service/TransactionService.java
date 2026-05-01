package cajero.service;

import cajero.domain.Transaction;

import java.util.List;

public interface TransactionService {
    boolean deposit(String accountNumber, double amount);
    boolean withdraw(String accountNumber, String cardNumber, String pin, double amount);

    List<Transaction> listAll();
    List<Transaction> listByAccount(String accountNumber);
    Transaction findById(int id);
    boolean deleteById(int id);
}
