package cajero.domain;

import java.util.Scanner;

public class Customer {
    private static Scanner sc = new Scanner(System.in);

    //Atributes

    private int id;
    private String name;
    private String email;
    private String phone;
    private String user;
    private String password;

    // Constructores

    public Customer(){}

    public Customer(String name) {
        this.name = name;
    }

    public Customer(int id, String name, String email, String password, String user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public static Customer createUser(){
        Customer customer = new Customer();

        System.out.println("---Register New Customer---");

        System.out.println("Enter the customer ID ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid ID. Enter a number: ");
            sc.next();
        }
        int id = sc.nextInt();
        customer.setId(id);
        sc.nextLine();

        System.out.println("Enter the customer name ");
        String name = sc.nextLine();
        customer.setName(name);

        System.out.println("Enter the customer email address ");
        String email = sc.nextLine();
        while (!email.contains("@") || !email.contains(".")) {
            System.out.println("Error: Invalid email format. Must contain '@' and a domain (ej: user@gmail.com)");
            System.out.print("Try again");
            email = sc.nextLine();
        }
        customer.setEmail(email);

        System.out.println("Enter the customer phone number ");
        String phone = sc.nextLine();
        customer.setPhone(phone);

        System.out.println("Enter the customer user name ");
        String user = sc.nextLine();
        customer.setUser(user);

        System.out.println("Enter the customer password ");
        String password = sc.nextLine();
        customer.setPassword(password);

        return customer;
    }

    public void getCustomerById(int id){
        if (id == this.id){
            System.out.println("Customer ID: " + this.id + "\n" +
                    "Name: " + this.name + "\n"+
                    "Email" + this.email + "\n" +
                    "Phone" + this.phone + "\n"+
                    "User: ****" + this.user + "\n"+
                    "Password: ***" + this.password);

        }else {
            System.out.println("Id not found. Please try again");
        }
    }
}
