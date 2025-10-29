package com.casino.Card.model;

import com.casino.User.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;
    private Date validDate;
    private String ownerName;
    private int cvc;

    @OneToOne(mappedBy = "card")
    private User user;
}

