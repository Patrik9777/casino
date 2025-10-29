package com.casino.User.model;

import com.casino.Card.model.Card;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @OneToOne(optional = true)
    @JoinColumn(name = "card_id")
    private Card card;
    private Integer balance;
}
