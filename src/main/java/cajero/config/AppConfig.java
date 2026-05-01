package cajero.config;

import cajero.repository.*;
import cajero.service.*;
import cajero.userinterface.MenuCajero;
import cajero.view.AccountView;
import cajero.view.CardView;
import cajero.view.CustomerView;
import cajero.view.TransactionView;

public class AppConfig {

    public static MenuCajero createMenuCajero() {

        CustomerRepository customerRepository = new CustomerRepository();
        AccountRepository accountRepository = new AccountRepository(customerRepository);
        CardRepository cardRepository = new CardRepository(customerRepository);
        TransactionRepository transactionRepository = new TransactionRepository();


        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        AccountService accountService = new AccountServiceImpl(accountRepository, customerRepository);
        CardService cardService = new CardServiceImpl(cardRepository, customerRepository);
        TransactionService transactionService = new TransactionServiceImpl(accountRepository, cardRepository, transactionRepository);


        CustomerView customerView = new CustomerView();
        AccountView accountView = new AccountView();
        CardView cardView = new CardView();
        TransactionView transactionView = new TransactionView();

        return new MenuCajero(customerService, accountService, cardService, transactionService,
                customerView, accountView, cardView, transactionView);
    }
}
