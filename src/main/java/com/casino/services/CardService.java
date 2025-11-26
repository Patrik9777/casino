package com.casino.services;

import com.casino.models.Card;
import com.casino.models.User;
import com.casino.repositories.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveOrUpdateCard(User user, String cardNumber, String ownerName, String cvc) {
        Card card = user.card != null ? user.card : new Card();
        card.setCardNumber(cardNumber);
        card.setLegacyNumber(cardNumber);
        card.setOwnerName(ownerName);
        card.setCvc(cvc);
        card.setUser(user);
        return cardRepository.save(card);
    }
}

