package com.casino.Card.repository;

import com.casino.Card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Long, Card> {

}
