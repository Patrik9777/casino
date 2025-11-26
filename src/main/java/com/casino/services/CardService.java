package com.casino.services;

import com.casino.models.Card;
import com.casino.models.User;
import com.casino.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveOrUpdateCard(User user, String cardNumber, String ownerName, String cvc) {
        Optional<Card> existingCard = cardRepository.findByCardNumber(cardNumber);
        if (existingCard.isPresent()) {
            Card found = existingCard.get();
            if (found.getUser() != null) {
                if (found.getUser().id == user.id) {
                    throw new IllegalStateException("Ez a kártya már el van mentve a profilodhoz.");
                } else {
                    throw new IllegalStateException("Ez a kártyaszám már egy másik felhasználóhoz tartozik.");
                }
            }
            // card exists in db but currently nincs user (legacy adat), így társítjuk
            found.setOwnerName(ownerName);
            found.setCvc(cvc);
            found.setLegacyNumber(cardNumber);
            found.setUser(user);
            user.card = found;
            return cardRepository.save(found);
        }

        Card card = user.card != null ? user.card : new Card();
        card.setCardNumber(cardNumber);
        card.setLegacyNumber(cardNumber);
        card.setOwnerName(ownerName);
        card.setCvc(cvc);
        card.setUser(user);
        return cardRepository.save(card);
    }
}

