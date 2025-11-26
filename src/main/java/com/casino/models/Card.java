package com.casino.models;

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
    private int id;

    public int number;
    public Date validDate;
    public String ownerName;
    public int cvc;

    @OneToOne(mappedBy = "card")
    public User user;
}

