package cajero.view;

import cajero.domain.Customer;
import cajero.util.TypeValidator;

import java.util.List;

public class CustomerView {

    public CustomerView(Object customerServiceImpl) {
    }

    public Customer createCustomerInteractive() {
        System.out.println("--- Registrar nuevo usuario/cliente ---");

        int id = TypeValidator.validateInt("Ingrese el ID del cliente: ");
        String name = TypeValidator.validateString("Ingrese el nombre del cliente: ");

        String email;
        while (true) {
            email = TypeValidator.validateString("Ingrese el correo del cliente: ");
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("Error: Formato de email inválido. Debe contener '@' y el dominio (ej: user@gmail.com).");
        }

        String phone = TypeValidator.validateString("Ingrese el teléfono del cliente: ");
        String user = TypeValidator.validateString("Ingrese el usuario de acceso: ");
        String password = TypeValidator.validateString("Ingrese la contraseña: ");

        Customer c = new Customer();
        c.setId(id);
        c.setName(name);
        c.setEmail(email);
        c.setPhone(phone);
        c.setUser(user);
        c.setPassword(password);
        return c;
    }

    public void printCustomer(Customer c) {
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("--- Detalle de Cliente ---");
        System.out.println("ID: " + c.getId());
        System.out.println("Nombre: " + c.getName());
        System.out.println("Email: " + c.getEmail());
        System.out.println("Teléfono: " + c.getPhone());
        System.out.println("Usuario: " + c.getUser());
        System.out.println("Contraseña: ***");
    }

    public void printCustomers(List<Customer> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("--- Clientes ---");
        for (Customer c : list) {
            System.out.println("ID: " + c.getId() + " | Nombre: " + c.getName() + " | Email: " + c.getEmail());
        }
    }
}
