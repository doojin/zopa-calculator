package com.zopa.loan.calculator.interest;

import org.springframework.stereotype.Component;

@Component
public class CompoundInterestCalculator implements InterestCalculator {

    private static final int PAYMENTS_PER_YEAR = 12;

    @Override
    public double calculateMonthlyPayment(double amount, double rate, int months) {
        double compoundRate = rate / PAYMENTS_PER_YEAR;
        return compoundRate * amount / (1 - Math.pow(1 + compoundRate, -months));
    }
}
