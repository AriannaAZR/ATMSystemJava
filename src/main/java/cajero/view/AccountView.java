package cajero.view;

import cajero.domain.Account;
import cajero.domain.Customer;
import cajero.util.TypeValidator;

public class AccountView {

    public Account createAccountInteractive(Customer owner) {
        System.out.println("--- Crear cuenta ---");
        String number = TypeValidator.validateString("Ingrese el número de cuenta: ");
        String type = TypeValidator.validateString("Ingrese el tipo de cuenta (Ahorros/Coriente): ");
        double initial = TypeValidator.validateDouble("Ingrese el depósito inicial: ");

        Account a = new Account();
        a.setNumber(number);
        a.setAccountType(type);
        a.setBalance(Math.max(initial, 0));
        a.setOwner(owner);
        return a;
    }

    public void printAccount(Account a) {
        if (a == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }
        System.out.println("--- Detalle de Cuenta ---");
        System.out.println("Número: " + a.getNumber());
        System.out.println("Tipo: " + a.getAccountType());
        System.out.println("Titular: " + (a.getOwner() != null ? a.getOwner().getName() : "N/A"));
        System.out.println("Saldo: $" + a.getBalance());
    }
}
