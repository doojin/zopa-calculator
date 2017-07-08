package com.zopa.loan.config;

import com.zopa.loan.calculator.LoanCalculator;
import com.zopa.loan.calculator.interest.CompoundInterestCalculator;
import com.zopa.loan.calculator.interest.InterestCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanCalculatorIntegrationTestConfig {

    @Bean
    public InterestCalculator interestCalculator() {
        return new CompoundInterestCalculator();
    }

    @Bean
    public LoanCalculator loanCalculator(@Autowired InterestCalculator interestCalculator) {
        return new LoanCalculator(interestCalculator);
    }
}
