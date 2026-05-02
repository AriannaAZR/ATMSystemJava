package cajero.userinterface;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;
import cajero.domain.Transaction;
import cajero.service.AccountService;
import cajero.service.CardService;
import cajero.service.CustomerService;
import cajero.service.TransactionService;
import cajero.util.TypeValidator;
import cajero.view.AccountView;
import cajero.view.CardView;
import cajero.view.CustomerView;
import cajero.view.TransactionView;

import java.util.List;
import java.util.Scanner;

public class MenuCajero {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final CardService cardService;
    private final TransactionService transactionService;

    private final CustomerView customerView;
    private final AccountView accountView;
    private final CardView cardView;
    private final TransactionView transactionView;

    private final Scanner sc = new Scanner(System.in);

    public MenuCajero(CustomerService customerService,
                      AccountService accountService,
                      CardService cardService,
                      TransactionService transactionService,
                      CustomerView customerView,
                      AccountView accountView,
                      CardView cardView,
                      TransactionView transactionView) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.cardService = cardService;
        this.transactionService = transactionService;
        this.customerView = customerView;
        this.accountView = accountView;
        this.cardView = cardView;
        this.transactionView = transactionView;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printHeader();
            printMenu();
            int option = TypeValidator.validateInt("Seleccione una opción: ");
            switch (option) {
                case 1 -> handleRegisterCustomer();
                case 2 -> handleOpenAccount();
                case 3 -> handleIssueCard();
                case 4 -> handleBalanceInquiry();
                case 5 -> handleDeposit();
                case 6 -> handleWithdrawal();
                case 7 -> handleShowCustomerById();
                case 8 -> handleListCustomers();

                case 9 -> handleUpdateCustomer();
                case 10 -> handleDeleteCustomer();

                case 11 -> handleUpdateAccount();
                case 12 -> handleDeleteAccount();

                case 13 -> handleUpdateCard();
                case 14 -> handleDeleteCard();

                case 15 -> handleListTransactionsByAccount();
                case 16 -> handleDeleteTransactionById();

                case 0 -> exit = true;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
    }

    private void printHeader() {
        System.out.println("=======================================");
        System.out.println("==========  CAJERO AUTOMÁTICO =========");
        System.out.println("=======================================");
    }

    private void printMenu() {
        System.out.println("1) Crear usuario (cliente)");
        System.out.println("2) Abrir cuenta para cliente");
        System.out.println("3) Emitir tarjeta para cliente");
        System.out.println("4) Consultar saldo");
        System.out.println("5) Depositar");
        System.out.println("6) Retirar");
        System.out.println("7) Ver cliente por ID");
        System.out.println("8) Listar clientes");
        System.out.println("9) Actualizar cliente");
        System.out.println("10) Eliminar cliente");
        System.out.println("11) Actualizar cuenta");
        System.out.println("12) Eliminar cuenta");
        System.out.println("13) Actualizar tarjeta");
        System.out.println("14) Eliminar tarjeta");
        System.out.println("15) Listar transacciones por cuenta");
        System.out.println("16) Eliminar transacción por ID");
        System.out.println("0) Salir");
    }

    private void handleRegisterCustomer() {
        Customer c = customerView.createCustomerInteractive();
        if (customerService.register(c) != null) {
            System.out.println("Cliente registrado correctamente.");
            customerView.printCustomer(c);
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    private void handleOpenAccount() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        Customer c = customerService.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account acc = accountView.createAccountInteractive(c);
        accountService.addAccount(id, acc);
        System.out.println("Cuenta creada y asociada.");
        accountView.printAccount(acc);
    }

    private void handleIssueCard() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        Customer c = customerService.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Card card = cardView.createCardInteractive(c);
        cardService.addCard(id, card);
        System.out.println("Tarjeta emitida y asociada.");
        cardView.printCard(card);
    }

    private void handleBalanceInquiry() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        Customer c = customerService.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account account = chooseAccount(id);
        if (account == null) {
            System.out.println("No hay cuentas disponibles para este cliente.");
            return;
        }
        accountView.printAccount(account);
    }

    private void handleDeposit() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account account = chooseAccount(id);
        if (account == null) {
            System.out.println("No hay cuentas disponibles para este cliente.");
            return;
        }
        double amount = transactionView.readDepositAmount();
        if (transactionService.deposit(account.getNumber(), amount)) {
            System.out.println("Depósito exitoso. Nuevo saldo: $" + account.getBalance());
        } else {
            System.out.println("No se pudo realizar el depósito.");
        }
    }

    private void handleWithdrawal() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account account = chooseAccount(id);
        if (account == null) {
            System.out.println("No hay cuentas disponibles para este cliente.");
            return;
        }
        Card card = chooseCard(id);
        if (card == null) {
            System.out.println("No hay tarjetas disponibles para este cliente.");
            return;
        }
        double amount = transactionView.readWithdrawalAmount();
        String pin = transactionView.readPin();
        if (transactionService.withdraw(account.getNumber(), card.getCardNumber(), pin, amount)) {
            System.out.println("Retiro exitoso. Nuevo saldo: $" + account.getBalance());
        } else {
            System.out.println("No se pudo realizar el retiro. Verifique saldo y PIN.");
        }
    }

    private void handleShowCustomerById() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        Customer c = customerService.findCustomer(id);
        customerView.printCustomer(c);
    }

    private void handleListCustomers() {
        customerView.printCustomers(customerService.listCustomers());
    }

    private void handleUpdateCustomer() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente a actualizar: ");
        Customer c = customerService.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("Deje vacío para mantener el valor actual.");
        c.setName(promptOptional("Nombre", c.getName()));
        c.setEmail(promptOptional("Email", c.getEmail()));
        c.setPhone(promptOptional("Teléfono", c.getPhone()));
        c.setUser(promptOptional("Usuario", c.getUser()));
        String pwd = promptOptional("Contraseña", "***");
        if (!"***".equals(pwd)) c.setPassword(pwd);
        Customer updated = customerService.updateCustomer(c);
        if (updated != null) {
            System.out.println("Cliente actualizado:");
            customerView.printCustomer(updated);
        } else {
            System.out.println("No se pudo actualizar el cliente.");
        }
    }

    private void handleDeleteCustomer() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente a eliminar: ");
        boolean ok = customerService.deleteCustomer(id);
        System.out.println(ok ? "Cliente eliminado." : "No se pudo eliminar (ID no existe).");
    }

    private void handleUpdateAccount() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account acc = chooseAccount(id);
        if (acc == null) return;
        System.out.println("Deje vacío para mantener el valor actual.");
        String type = promptOptional("Tipo de cuenta", acc.getAccountType());
        acc.setAccountType(type);
        String changeBal = promptOptional("¿Cambiar saldo? (s/n)", "n");
        if ("s".equalsIgnoreCase(changeBal)) {
            double bal = TypeValidator.validateDouble("Nuevo saldo: ");
            acc.setBalance(bal);
        }
        boolean ok = accountService.updateAccount(acc);
        System.out.println(ok ? "Cuenta actualizada." : "No se pudo actualizar la cuenta.");
    }

    private void handleDeleteAccount() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account acc = chooseAccount(id);
        if (acc == null) return;
        boolean ok = accountService.deleteAccountByNumber(acc.getNumber());
        System.out.println(ok ? "Cuenta eliminada." : "No se pudo eliminar la cuenta.");
    }

    private void handleUpdateCard() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Card card = chooseCard(id);
        if (card == null) return;
        System.out.println("Deje vacío para mantener el PIN actual.");
        String pin = promptOptional("Nuevo PIN (4 dígitos)", "");
        if (!pin.isBlank()) {
            if (pin.length() == 4 && pin.chars().allMatch(Character::isDigit)) {
                card.setEncryptedPin(pin);
            } else {
                System.out.println("PIN inválido, se mantiene el actual.");
            }
        }
        boolean blocked = TypeValidator.validateBoolean("¿Bloquear tarjeta? (true/false): ");
        card.setBlocked(blocked);
        boolean ok = cardService.updateCard(card);
        System.out.println(ok ? "Tarjeta actualizada." : "No se pudo actualizar la tarjeta.");
    }

    private void handleDeleteCard() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Card card = chooseCard(id);
        if (card == null) return;
        boolean ok = cardService.deleteCardByNumber(card.getCardNumber());
        System.out.println(ok ? "Tarjeta eliminada." : "No se pudo eliminar la tarjeta.");
    }

    private void handleListTransactionsByAccount() {
        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        if (customerService.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account acc = chooseAccount(id);
        if (acc == null) return;
        List<Transaction> txs = transactionService.listByAccount(acc.getNumber());
        transactionView.printTransactions(txs);
    }

    private void handleDeleteTransactionById() {
        int txId = transactionView.readTransactionId();
        boolean ok = transactionService.deleteById(txId);
        System.out.println(ok ? "Transacción eliminada." : "No se encontró la transacción.");
    }

    private String promptOptional(String label, String current) {
        System.out.print(label + " [" + current + "]: ");
        String line = sc.nextLine();
        return line == null || line.isBlank() ? current : line.trim();
    }

    private Account chooseAccount(int customerId) {
        List<Account> accounts = accountService.getAccountsByCustomer(customerId);
        if (accounts.isEmpty()) return null;

        System.out.println("--- Cuentas del cliente ---");
        for (Account a : accounts) {
            System.out.println("- Número: " + a.getNumber() + " | Tipo: " + a.getAccountType() + " | Saldo: $" + a.getBalance());
        }
        String number = TypeValidator.validateString("Ingrese el número de cuenta: ");
        for (Account a : accounts) {
            if (number.equals(a.getNumber())) {
                return a;
            }
        }
        System.out.println("Cuenta no encontrada.");
        return null;
    }

    private Card chooseCard(int customerId) {
        List<Card> cards = cardService.getCardsByCustomer(customerId);
        if (cards.isEmpty()) return null;

        System.out.println("--- Tarjetas del cliente ---");
        for (Card card : cards) {
            System.out.println("- ID: " + card.getCardId() + " | Número: " + card.getCardNumber() + " | Bloqueada: " + card.isBlocked());
        }
        String number = TypeValidator.validateString("Ingrese el número de la tarjeta: ");
        for (Card card : cards) {
            if (number.equals(card.getCardNumber())) {
                return card;
            }
        }
        System.out.println("Tarjeta no encontrada.");
        return null;
    }
}
