package com.zopa.loan.calculator;

import com.zopa.loan.calculator.interest.CompoundInterestCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompoundInterestCalculatorTest {

    private static final double PRECISION = 0.01;

    private CompoundInterestCalculator calculator;

    @Before
    public void setUp() {
        calculator = new CompoundInterestCalculator();
    }

    @Test
    public void calculatePayment() {
        double initialAmount = 1_000;
        double rate = 0.07;
        int periodMonths = 36;

        assertEquals(30.88, calculator.calculateMonthlyPayment(initialAmount, rate, periodMonths), PRECISION);
    }

}