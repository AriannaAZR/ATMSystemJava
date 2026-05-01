package cajero.userinterface;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;
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

public class MenuCajero {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final CardService cardService;
    private final TransactionService transactionService;

    private final CustomerView customerView;
    private final AccountView accountView;
    private final CardView cardView;
    private final TransactionView transactionView;

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
