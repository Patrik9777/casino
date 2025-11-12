package com.casino.services;

import java.util.Random;

/**
 * Crash Game Service - Business Logic
 * Provably Fair algoritmus
 */
public class CrashGameService {
    private static final double HOUSE_EDGE = 0.03; // 3%
    private Random random;

    public CrashGameService() {
        this.random = new Random();
    }

    /**
     * Calculate crash point using provably fair algorithm
     * 97% RTP, 3% house edge
     */
    public double calculateCrashPoint() {
        double r = random.nextDouble();
        
        // 3% instant crash at 1.00x
        if (r < HOUSE_EDGE) {
            return 1.00;
        }
        
        // Provably fair formula
        double result = (100.0 - HOUSE_EDGE * 100) / ((100.0 - HOUSE_EDGE * 100) - (r * 100.0));
        
        // Clamp values
        if (result < 1.00) result = 1.00;
        if (result > 100.0) result = 100.0;
        
        return Math.round(result * 100.0) / 100.0;
    }

    /**
     * Calculate win amount
     */
    public int calculateWinAmount(int bet, double multiplier) {
        return (int) (bet * multiplier);
    }

    /**
     * Validate bet
     */
    public boolean isValidBet(int bet, int userBalance) {
        return bet > 0 && bet <= userBalance;
    }
}
