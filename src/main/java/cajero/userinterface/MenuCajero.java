package cajero.userinterface;

import cajero.domain.Account;
import cajero.domain.Card;
import cajero.domain.Customer;
import cajero.domain.Transaction;
import cajero.repository.CustomerRepository;
import cajero.service.CustomerService;
import cajero.service.CustomerServiceImpl;
import cajero.view.CustomerView;
import cajero.util.TypeValidator;

import java.util.List;
import java.util.Scanner;

public class MenuCajero {

    private final Scanner sc = new Scanner(System.in);
    private final CustomerService service = new CustomerServiceImpl(new CustomerRepository());
    private final CustomerView view = new CustomerView();

    public void start() {
        boolean exit = false;
        while (!exit) {
            printHeader();
            printMenu();
            int option = readInt("Seleccione una opción: ");
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
            if (!exit) {
                System.out.println();
                System.out.println("Presione ENTER para continuar...");
                sc.nextLine();
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
        Customer c = view.createCustomerInteractive();
        if (service.register(c) != null) {
            System.out.println("Cliente registrado correctamente: " + c.getName() + " (ID: " + c.getId() + ")");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    private void handleOpenAccount() {
        int id = readInt("Ingrese el ID del cliente: ");
        Customer c = service.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account acc = service.openAccountInteractive(id);
        if (acc != null) {
            System.out.println("Cuenta creada y asociada a " + c.getName());
            acc.getAccountDetails();
        } else {
            System.out.println("No se pudo crear la cuenta.");
        }
    }

    private void handleIssueCard() {
        int id = readInt("Ingrese el ID del cliente: ");
        Customer c = service.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Card card = service.issueCardInteractive(id);
        if (card != null) {
            System.out.println("Tarjeta emitida y asociada a " + c.getName());
            card.getCardById(card.getCardId());
        } else {
            System.out.println("No se pudo emitir la tarjeta.");
        }
    }

    private void handleBalanceInquiry() {
        int id = readInt("Ingrese el ID del cliente: ");
        Customer c = service.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account account = chooseAccount(id);
        if (account == null) {
            System.out.println("No hay cuentas disponibles para este cliente.");
            return;
        }
        account.getAccountDetails();
    }

    private void handleDeposit() {
        int id = readInt("Ingrese el ID del cliente: ");
        if (service.findCustomer(id) == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Account account = chooseAccount(id);
        if (account == null) {
            System.out.println("No hay cuentas disponibles para este cliente.");
            return;
        }
        // Para depósito, la tarjeta no es necesaria. Se pasa una tarjeta dummy.
        Transaction.createTransaction(account, new Card(0, "", "", ""), "Deposit");
    }

    private void handleWithdrawal() {
        int id = readInt("Ingrese el ID del cliente: ");
        if (service.findCustomer(id) == null) {
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
        Transaction.createTransaction(account, card, "Withdrawal");
    }

    private void handleShowCustomerById() {
        int id = readInt("Ingrese el ID del cliente: ");
        Customer c = service.findCustomer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
        } else {
            view.printCustomer(c);
        }
    }

    private void handleListCustomers() {
        List<Customer> list = service.listCustomers();
        if (list.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("--- Clientes ---");
        for (Customer c : list) {
            System.out.println("ID: " + c.getId() + " | Nombre: " + c.getName() + " | Email: " + c.getEmail());
        }
    }

    private Account chooseAccount(int customerId) {
        List<Account> accounts = service.getAccountsByCustomer(customerId);
        if (accounts.isEmpty()) return null;

        System.out.println("--- Cuentas del cliente ---");
        for (Account a : accounts) {
            System.out.println("- Número: " + a.getNumber() + " | Tipo: " + a.getAccountType() + " | Saldo: $" + a.getBalance());
        }
        String number = readLine("Ingrese el número de cuenta: ");
        for (Account a : accounts) {
            if (number.equals(a.getNumber())) {
                return a;
            }
        }
        System.out.println("Cuenta no encontrada.");
        return null;
    }

    private Card chooseCard(int customerId) {
        List<Card> cards = service.getCardsByCustomer(customerId);
        if (cards.isEmpty()) return null;

        System.out.println("--- Tarjetas del cliente ---");
        for (Card card : cards) {
            System.out.println("- ID: " + card.getCardId() + " | Número: " + card.getCardNumber() + " | Bloqueada: " + card.isBlocked());
        }
        String number = readLine("Ingrese el número de la tarjeta: ");
        for (Card card : cards) {
            if (number.equals(card.getCardNumber())) {
                return card;
            }
        }
        System.out.println("Tarjeta no encontrada.");
        return null;
    }

    private int readInt(String prompt) {
        return TypeValidator.validateInt(prompt);
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
