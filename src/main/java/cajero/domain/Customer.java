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


}
