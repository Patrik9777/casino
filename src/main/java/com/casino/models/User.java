package com.casino.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String username;
    public String password;
    public String email;
    @OneToOne(optional = true)
    @JoinColumn(name = "card_id")
    public Card card;
    public Integer balance;
}
