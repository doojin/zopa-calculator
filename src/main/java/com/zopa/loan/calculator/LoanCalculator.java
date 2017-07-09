package com.zopa.loan.calculator;

import com.zopa.loan.calculator.interest.InterestCalculator;
import com.zopa.loan.filter.OfferFilter;
import com.zopa.loan.model.Loan;
import com.zopa.loan.model.Offer;
import com.zopa.loan.util.OfferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoanCalculator {

    private final InterestCalculator interestCalculator;
    private final OfferFilter offerFilter;

    @Autowired
    public LoanCalculator(InterestCalculator interestCalculator, OfferFilter offerFilter) {
        this.interestCalculator = interestCalculator;
        this.offerFilter = offerFilter;
    }

    public Optional<Loan> calculate(double amount, List<Offer> offers, int months) {
        List<Offer> filteredOffers = offerFilter.filterByLowerRate(offers, amount);

        if (OfferUtils.getTotalAmount(filteredOffers) != amount) {
            return Optional.empty();
        }

        double rate = calculateRate(filteredOffers, amount);
        double monthlyPayment = calculateMonthlyPayment(filteredOffers, months);
        double totalPayment= monthlyPayment * months;

        return Optional.of(new Loan(amount, rate, monthlyPayment, totalPayment));
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
