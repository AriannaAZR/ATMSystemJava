package cajero;

import java.time.LocalDate;
import java.util.Scanner;

public class Card {
    private static Scanner sc = new Scanner(System.in);

    //Atributes

    private int cardId;
    private String cardNumber;
    private String cardName;
    private String encryptedPin;
    private LocalDate expirationDate;
    private boolean isBlocked;

    public Card(int cardId, String cardNumber, String pin, String cardName) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.encryptedPin = pin;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getEncryptedPin() {
        return encryptedPin;
    }

    public void setEncryptedPin(String encryptedPin) {
        this.encryptedPin = encryptedPin;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public static Card isueCard(Card card, Customer customer) {
        System.out.println("--- Issuing New Card ---");

        System.out.println("Enter Card ID: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid Card ID");
            sc.next();
        }
        int cardId = sc.nextInt();
        card.setCardId(cardId);
        sc.nextLine();

        System.out.print("Enter Card Number (16 digits): ");
        String number = sc.nextLine();
        while (number.length() != 16) {
            System.out.print("Error: Must be 16 digits: ");
            number = sc.nextLine();
        }
        card.setCardNumber(number);

        card.setCardName(customer.getName());
        System.out.println("Card holder name set to: " + card.getCardName());

        System.out.print("Set a 4-digit PIN: ");
        String pin = sc.nextLine();
        while (pin.length() != 4) {
            System.out.print("Error: PIN must be 4 digits: ");
            pin = sc.nextLine();
        }
        card.setEncryptedPin(pin);

        System.out.print("Enter Expiration Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();
        boolean dateOk = false;

        while (!dateOk) {
            try {
                card.setExpirationDate(LocalDate.parse(dateInput));
                dateOk = true;
            } catch (Exception e) {
                System.out.print("Invalid format. Enter date as YYYY-MM-DD: ");
                dateInput = sc.nextLine();
            }
        }

        card.setBlocked(false);
        System.out.println("Card successfully issued to " + customer.getName() + "!");

        return card;
    }
    public void getCardById(int id){
        if (id == this.cardId){
            System.out.println("---Card Details---");
            System.out.println("Card ID: " +  this.cardId + "\n"+
            "Number: " + this.cardNumber + "\n"+
            "Cardholder: "+ this.cardName + "\n"+
            "Expires: ***" + this.expirationDate + "\n"+
            "Status: " + this.isBlocked );
        }else {
            System.out.println("Card ID: " + id + " not found");
        }

    }
}
