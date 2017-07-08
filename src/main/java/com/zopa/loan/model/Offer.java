package com.zopa.loan.model;

public class Offer {

    private final double rate;
    private final double amount;

    public Offer(double rate, double amount) {
        this.rate = rate;
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public double getAmount() {
        return amount;
    }
}
