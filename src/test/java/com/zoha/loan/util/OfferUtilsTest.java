package com.zoha.loan.util;

import com.zoha.loan.model.Offer;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OfferUtilsTest {

    @Test
    public void getTotalAmount_shouldReturnSumOfOfferAmounts() {
        List<Offer> offers = asList(
                new Offer(0.1, 10),
                new Offer(0.1, 20),
                new Offer(0.1, 30));

        assertThat(OfferUtils.getTotalAmount(offers), equalTo(60.0));
    }

}