package com.zopa.loan.config;

import com.zopa.loan.calculator.LoanCalculator;
import com.zopa.loan.calculator.interest.CompoundInterestCalculator;
import com.zopa.loan.calculator.interest.InterestCalculator;
import com.zopa.loan.filter.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanCalculatorIntegrationTestConfig {

    @Bean
    public OfferFilter offerFilter() {
        return new OfferFilter();
    }

    @Bean
    public InterestCalculator interestCalculator() {
        return new CompoundInterestCalculator();
    }

    @Bean
    @Autowired
    public LoanCalculator loanCalculator(InterestCalculator interestCalculator, OfferFilter offerFilter) {
        return new LoanCalculator(interestCalculator, offerFilter);
    }
}
