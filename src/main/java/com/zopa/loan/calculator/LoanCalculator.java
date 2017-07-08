package com.zopa.loan.calculator;

import com.zopa.loan.calculator.interest.InterestCalculator;
import com.zopa.loan.model.Loan;
import com.zopa.loan.model.Offer;
import com.zopa.loan.util.OfferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanCalculator {

    private final InterestCalculator interestCalculator;

    @Autowired
    public LoanCalculator(InterestCalculator interestCalculator) {
        this.interestCalculator = interestCalculator;
    }

    public Loan calculate(List<Offer> requestedOffers, int months) {
        double requestedAmount = OfferUtils.getTotalAmount(requestedOffers);
        double rate = calculateRate(requestedOffers, requestedAmount);
        double monthlyPayment = calculateMonthlyPayment(requestedOffers, months);
        double totalPayment= monthlyPayment * months;

        return new Loan(requestedAmount, rate, monthlyPayment, totalPayment);
    }

    private double calculateMonthlyPayment(List<Offer> requestedOffers, int months) {
        return requestedOffers.stream()
                .mapToDouble(offer -> interestCalculator.calculateMonthlyPayment(offer.getAmount(), offer.getRate(), months))
                .sum();
    }

    private double calculateRate(List<Offer> requestedOffers, double requestedAmount) {
        return requestedOffers.stream()
                .mapToDouble(offer -> offer.getRate() * offer.getAmount() / requestedAmount)
                .sum();
    }
}
