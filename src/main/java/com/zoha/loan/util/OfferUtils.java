package com.zoha.loan.util;

import com.zoha.loan.model.Offer;

import java.util.List;

public class OfferUtils {

    public static double getTotalAmount(List<Offer> offers) {
        return offers.stream()
                .mapToDouble(Offer::getAmount)
                .sum();
    }
}
