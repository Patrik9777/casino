package com.casino.Card.model;

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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @OneToOne
    private long Id;
    private int number;
    private Date validDate;
    private String ownerName;
    private int cvc;
}
