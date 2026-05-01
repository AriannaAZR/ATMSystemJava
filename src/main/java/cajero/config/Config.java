package cajero.config;

import cajero.repository.CustomerRepository;
import cajero.service.CustomerServiceImpl;
import cajero.userinterface.MenuCajero;
import cajero.view.CustomerView;

public class Config {

 public static MenuCajero createMenuCajero(Object CustomerServiceImpl, Object CustomerView){

     CustomerRepository customerRepository = new CustomerRepository();

     CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository);

     CustomerView customerView = new CustomerView(CustomerServiceImpl);



     return new MenuCajero(CustomerView);
 }
}
