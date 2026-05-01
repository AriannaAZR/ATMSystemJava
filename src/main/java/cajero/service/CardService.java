package cajero.service;

import cajero.domain.Card;

import java.util.List;

public interface CardService {
    void addCard(int customerId, Card card);
    List<Card> getCardsByCustomer(int customerId);
    Card findCardByNumber(String cardNumber);
    boolean updateCard(Card card);
    boolean deleteCardByNumber(String cardNumber);
}
