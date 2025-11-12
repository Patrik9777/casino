package com.casino.controllers;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Random;

@Controller
public class BlackjackGameController {

    private final Random random = new Random();

    public int drawCard() {
        return random.nextInt(11) + 1;
    }

    public int calculateScore(List<Integer> cards) {
        int score = 0;
        int aces = 0;

        for (int card : cards) {
            if (card == 1) {
                aces++;
                score += 11;
            } else {
                score += card;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }
}
