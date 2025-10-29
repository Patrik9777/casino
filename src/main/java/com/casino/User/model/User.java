package com.casino.User.model;

import com.casino.Card.model.Card;
import jakarta.persistence.*;
import lombok.*;

/**
 * User Entity - Spring Boot kompatibilis
 * @Entity annotációval JPA-hoz használható
 */
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
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private Integer balance;
}
