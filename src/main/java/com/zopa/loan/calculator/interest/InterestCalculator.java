package com.zopa.loan.calculator.interest;

public interface InterestCalculator {
    double calculateMonthlyPayment(double amount, double rate, int months);
}
