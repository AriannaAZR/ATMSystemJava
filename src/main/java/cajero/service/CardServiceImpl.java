package cajero.service;

import cajero.domain.Card;
import cajero.domain.Customer;
import cajero.repository.CardRepository;
import cajero.repository.CustomerRepository;

import java.util.List;

public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    public CardServiceImpl(CardRepository cardRepository, CustomerRepository customerRepository) {
        this.cardRepository = cardRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCard(int customerId, Card card) {
        if (card == null) return;
        Customer c = customerRepository.findCustomerById(customerId);
        if (c == null) return;
        if (card.getCardName() == null || card.getCardName().isBlank()) {
            card.setCardName(c.getName());
        }
        cardRepository.addCard(customerId, card);
    }

    @Override
    public List<Card> getCardsByCustomer(int customerId) {
        return cardRepository.getCardsByCustomer(customerId);
    }

    @Override
    public Card findCardByNumber(String cardNumber) {
        return cardRepository.findCardByNumber(cardNumber);
    }

    @Override
    public boolean updateCard(Card card) {
        return cardRepository.updateCard(card);
    }

    @Override
    public boolean deleteCardByNumber(String cardNumber) {
        return cardRepository.deleteCardByNumber(cardNumber);
    }
}
