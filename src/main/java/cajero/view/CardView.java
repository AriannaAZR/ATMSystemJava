package cajero.view;

import cajero.domain.Card;
import cajero.domain.Customer;
import cajero.util.TypeValidator;

import java.time.LocalDate;

public class CardView {

    public Card createCardInteractive(Customer customer) {
        System.out.println("--- Emitir tarjeta ---");
        int id = TypeValidator.validateInt("Ingrese el ID de la tarjeta: ");

        String number;
        while (true) {
            number = TypeValidator.validateString("Ingrese el número de la tarjeta (16 dígitos): ");
            if (number.length() == 16 && number.chars().allMatch(Character::isDigit)) break;
            System.out.println("Error: Debe tener 16 dígitos.");
        }

        String pin;
        while (true) {
            pin = TypeValidator.validateString("Defina un PIN de 4 dígitos: ");
            if (pin.length() == 4 && pin.chars().allMatch(Character::isDigit)) break;
            System.out.println("Error: El PIN debe tener 4 dígitos.");
        }

        LocalDate expiration;
        while (true) {
            try {
                String d = TypeValidator.validateString("Ingrese fecha de expiración (YYYY-MM-DD): ");
                expiration = LocalDate.parse(d);
                break;
            } catch (Exception e) {
                System.out.println("Formato inválido. Ejemplo: 2030-12-31");
            }
        }

        Card card = new Card(id, number, pin, customer.getName());
        card.setExpirationDate(expiration);
        card.setBlocked(false);
        return card;
    }

    public void printCard(Card card) {
        if (card == null) {
            System.out.println("Tarjeta no encontrada.");
            return;
        }
        System.out.println("--- Detalle de Tarjeta ---");
        System.out.println("ID: " + card.getCardId());
        System.out.println("Número: " + card.getCardNumber());
        System.out.println("Titular: " + card.getCardName());
        System.out.println("Expira: " + card.getExpirationDate());
        System.out.println("Bloqueada: " + card.isBlocked());
    }
}
