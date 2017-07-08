package com.zopa.loan.calculator;

import com.zopa.loan.config.LoanCalculatorIntegrationTestConfig;
import com.zopa.loan.model.Loan;
import com.zopa.loan.model.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LoanCalculatorIntegrationTestConfig.class)
public class LoanCalculatorIntegrationTest {

    private static final double PRECISION = 0.01;

    @Autowired
    private LoanCalculator loanCalculator;

    @Test
    public void calculate_oneOffer() {
        List<Offer> offers = singletonList(new Offer(0.07, 1_000));

        Loan loan = loanCalculator.calculate(offers, 36);

        assertLoan(loan, 1_000, 0.07, 30.87, 1111.57);
    }

    @Test
    public void calculate_whenMultipleOffersWithSameRate_finalRateShouldRemainTheSame() {
        List<Offer> offers = asList(
                new Offer(0.07, 700),
                new Offer(0.07, 300));

        Loan loan = loanCalculator.calculate(offers, 36);

        assertLoan(loan, 1_000, 0.07, 30.87, 1111.57);
    }

    @Test
    public void calculate_multipleOffersWithDifferentRates() {
        // First offer: 1000 with 10% rate -> Payment will be: 1,161.62
        // Second offer: 500 with 5% rate -> Payment will be: 539.48
        // Total payment is: 1,161.62 + 539.48 ~= 1,701 (it is 1,701 / 36 ~= 47.25 per month)
        // If initial amount is 1,500 and payment is 1,701 then rate is ~= 8,3%

        List<Offer> offers = asList(
                new Offer(0.1, 1000),
                new Offer(0.05, 500));

        Loan loan = loanCalculator.calculate(offers, 36);

        assertLoan(loan, 1_500, 0.083, 47.25, 1701.09);
    }

    private static void assertLoan(Loan loan, double expectedRequestedAmount, double expectedRate,
                                   double expectedMonthlyRepayment, double expectedTotalRepayment) {

        assertEquals(expectedRequestedAmount, loan.getRequestedAmount(), PRECISION);
        assertEquals(expectedRate, loan.getRate(), PRECISION);
        assertEquals(expectedMonthlyRepayment, loan.getMonthlyRepayment(), PRECISION);
        assertEquals(expectedTotalRepayment, loan.getTotalRepayment(), PRECISION);
    }

}