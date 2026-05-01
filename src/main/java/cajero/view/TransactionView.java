package cajero.view;

import cajero.domain.Transaction;
import cajero.util.TypeValidator;

import java.util.List;

public class TransactionView {

    public double readDepositAmount() {
        double amount;
        while (true) {
            amount = TypeValidator.validateDouble("Ingrese el monto a depositar: ");
            if (amount > 0) return amount;
            System.out.println("El monto debe ser positivo.");
        }
    }

    public double readWithdrawalAmount() {
        double amount;
        while (true) {
            amount = TypeValidator.validateDouble("Ingrese el monto a retirar: ");
            if (amount > 0) return amount;
            System.out.println("El monto debe ser positivo.");
        }
    }

    public String readPin() {
        String pin;
        while (true) {
            pin = TypeValidator.validateString("Ingrese su PIN de 4 dígitos: ");
            if (pin.length() == 4 && pin.chars().allMatch(Character::isDigit)) return pin;
            System.out.println("PIN inválido.");
        }
    }

    public void printTransactions(List<Transaction> txs) {
        if (txs == null || txs.isEmpty()) {
            System.out.println("No hay transacciones para mostrar.");
            return;
        }
        System.out.println("--- Transacciones ---");
        for (Transaction t : txs) {
            System.out.println("ID: " + t.getId() + " | Tipo: " + t.getTransactionType() +
                    " | Monto: $" + t.getAmount() + " | Fecha: " + t.getDate());
        }
    }

    public int readTransactionId() {
        return TypeValidator.validateInt("Ingrese el ID de la transacción: ");
    }
}
