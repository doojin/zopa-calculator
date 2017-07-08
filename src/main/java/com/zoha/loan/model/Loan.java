package com.zoha.loan.model;

public class Loan {

    private final double requestedAmount;
    private final double rate;
    private final double monthlyRepayment;
    private final double totalRepayment;

    public Loan(double requestedAmount, double rate, double monthlyRepayment, double totalRepayment) {
        this.requestedAmount = requestedAmount;
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getRate() {
        return rate;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }
}
