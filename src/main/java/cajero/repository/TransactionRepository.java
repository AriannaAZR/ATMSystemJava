package cajero.repository;

import cajero.domain.Transaction;

import java.time.LocalDateTime;
import java.util.*;

public class TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();
    private final Map<String, List<Transaction>> txByAccount = new HashMap<>();
    private int nextId = 1;

    public Transaction save(String accountNumber, Transaction t) {
        if (t == null || accountNumber == null) return null;
        if (t.getId() == 0) {
            t.setId(nextId++);
        }
        if (t.getDate() == null) {
            t.setDate(LocalDateTime.now());
        }
        transactions.add(t);
        txByAccount.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(t);
        return t;
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    public Transaction findById(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public List<Transaction> findByAccountNumber(String accountNumber) {
        return new ArrayList<>(txByAccount.getOrDefault(accountNumber, Collections.emptyList()));
    }

    public boolean deleteById(int id) {
        boolean removed = transactions.removeIf(t -> t.getId() == id);
        if (removed) {
            for (List<Transaction> list : txByAccount.values()) {
                list.removeIf(t -> t.getId() == id);
            }
        }
        return removed;
    }
}
