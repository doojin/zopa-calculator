package com.zoha.loan.calculator;

import org.springframework.stereotype.Component;

@Component
public class CompoundInterestCalculator {

    private static final int PAYMENTS_PER_YEAR = 12;
    private static final int MONTHS_PER_YEAR = 12;

    public double calculatePayment(double amount, double rate, int months) {
        double yearsOfPayment = (double) months / MONTHS_PER_YEAR;
        return amount * Math.pow(1 + rate / PAYMENTS_PER_YEAR, PAYMENTS_PER_YEAR * yearsOfPayment);
    }
}
