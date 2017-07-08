package com.zoha.loan.calculator;

import com.zoha.loan.calculator.interest.CompoundInterestCalculator;
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

        assertEquals(calculator.calculateMonthlyPayment(initialAmount, rate, periodMonths), 30.88, PRECISION);
    }

}