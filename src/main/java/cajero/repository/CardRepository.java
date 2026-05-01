package cajero.repository;

import cajero.domain.Card;

import java.util.List;

public class CardRepository {

    private final CustomerRepository customerRepository;

    public CardRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCard(int customerId, Card card) {
        customerRepository.addCard(customerId, card);
    }

    public List<Card> getCardsByCustomer(int customerId) {
        return customerRepository.getCardsByCustomer(customerId);
    }

    public Card findCardByNumber(String cardNumber) {
        return customerRepository.findCardByNumber(cardNumber);
    }

    public boolean updateCard(Card updated) {
        return customerRepository.updateCard(updated);
    }

    public boolean deleteCardByNumber(String cardNumber) {
        return customerRepository.deleteCardByNumber(cardNumber);
    }
}
