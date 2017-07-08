package com.zoha.loan.filter;

import com.zoha.loan.model.Offer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OfferFilter {

    public List<Offer> filterByLowerRate(List<Offer> offers, double amount) {
        List<Offer> sortedByRate = offers.stream().sorted(this::byRate).collect(toList());
        List<Offer> lowerRateOffers = new ArrayList<>();

        for (Offer offer: sortedByRate) {
            if (amount == 0) break;

            Offer partialOffer = createPartialOffer(amount, offer);
            lowerRateOffers.add(partialOffer);

            amount -= partialOffer.getAmount();
        }

        return lowerRateOffers;
    }

    private int byRate(Offer offer1, Offer offer2) {
        return Double.compare(offer1.getRate(), offer2.getRate());
    }

    private Offer createPartialOffer(double amount, Offer offer) {
        double partialAmount = Math.min(amount, offer.getAmount());
        return new Offer(offer.getRate(), partialAmount);
    }
}
