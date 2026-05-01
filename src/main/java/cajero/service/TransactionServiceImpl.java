package cajero.service;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Transaction;
import cajero.repository.AccountRepository;
import cajero.repository.CardRepository;
import cajero.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository,
                                  CardRepository cardRepository,
                                  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean deposit(String accountNumber, double amount) {
        Account acc = accountRepository.findAccountByNumber(accountNumber);
        if (acc == null || amount <= 0) return false;

        acc.setBalance(acc.getBalance() + amount);

        Transaction t = new Transaction();
        t.setTransactionType("Deposit");
        t.setAmount(amount);
        t.setDate(LocalDateTime.now());
        transactionRepository.save(accountNumber, t);
        return true;
    }

    @Override
    public boolean withdraw(String accountNumber, String cardNumber, String pin, double amount) {
        Account acc = accountRepository.findAccountByNumber(accountNumber);
        Card card = cardRepository.findCardByNumber(cardNumber);
        if (acc == null || card == null) return false;
        if (card.isBlocked()) return false;
        if (!card.getEncryptedPin().equals(pin)) return false;
        if (amount <= 0 || acc.getBalance() < amount) return false;

        acc.setBalance(acc.getBalance() - amount);

        Transaction t = new Transaction();
        t.setTransactionType("Withdrawal");
        t.setAmount(amount);
        t.setDate(LocalDateTime.now());
        transactionRepository.save(accountNumber, t);
        return true;
    }

    @Override
    public List<Transaction> listAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> listByAccount(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Transaction findById(int id) {
        return transactionRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return transactionRepository.deleteById(id);
    }
}
