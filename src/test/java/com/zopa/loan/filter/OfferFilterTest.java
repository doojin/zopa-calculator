package com.zopa.loan.filter;

import com.zopa.loan.model.Offer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class OfferFilterTest {

    private List<Offer> offers;
    private OfferFilter filter;

    @Before
    public void setUp() {
        offers = asList(
                new Offer(0.1, 100),
                new Offer(0.3, 300),
                new Offer(0.2, 200));

        filter = new OfferFilter();
    }

    @Test
    public void filterByLowerRate_shouldReturnOffersSortedByRate() {
        List<Offer> filtered = filter.filterByLowerRate(offers, 600);

        assertThat(filtered.size(), equalTo(3));
        assertOffer(filtered.get(0), 0.1, 100);
        assertOffer(filtered.get(1), 0.2, 200);
        assertOffer(filtered.get(2), 0.3, 300);
    }

    @Test
    public void filterByLowerRate_totalOffersAmountShouldBeEqualToRequestedAmount() {
        List<Offer> filtered = filter.filterByLowerRate(offers, 150);

        assertThat(filtered.stream().mapToDouble(Offer::getAmount).sum(), equalTo(150D));

        assertThat(filtered.size(), equalTo(2));
        assertOffer(filtered.get(0), 0.1, 100);
        assertOffer(filtered.get(1), 0.2, 50);
    }

    @Test
    public void filterByLowerRate_whenTotalAmountIsLessThanRequestedAmount_shouldReturnTotalAmount() {
        List<Offer> filtered = filter.filterByLowerRate(offers, 1000);

        assertThat(filtered.stream().mapToDouble(Offer::getAmount).sum(), equalTo(600D));

        assertThat(filtered.size(), equalTo(3));
        assertOffer(filtered.get(0), 0.1, 100);
        assertOffer(filtered.get(1), 0.2, 200);
        assertOffer(filtered.get(2), 0.3, 300);
    }

    private static void assertOffer(Offer offer, double expectedRate, double expectedAmount) {
        assertThat(offer.getRate(), equalTo(expectedRate));
        assertThat(offer.getAmount(), equalTo(expectedAmount));
    }

}