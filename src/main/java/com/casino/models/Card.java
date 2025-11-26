package com.casino.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "card_number", nullable = false, length = 24)
    private String cardNumber;

    @Column(name = "number", nullable = false, length = 24)
    private String legacyNumber;

    @Column(name = "owner_name", nullable = false, length = 128)
    private String ownerName;

    @Column(name = "cvc", nullable = false, length = 4)
    private String cvc;

    @OneToOne(mappedBy = "card")
    private User user;
}

