package com.zopa.loan.command;

import com.zopa.loan.calculator.LoanCalculator;
import com.zopa.loan.command.resolver.ArgumentResolver;
import com.zopa.loan.data.reader.FileDataReader;
import com.zopa.loan.model.Loan;
import com.zopa.loan.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoanCalculationCommand implements Command {

    private static final String MESSAGE_CANT_SATISFY_REQUEST =
            "We are not able to fully satisfy your request. You can try with a lower amount.";

    private static final int LOAN_PERIOD_MONTHS = 36;

    private final ArgumentResolver argumentResolver;
    private final FileDataReader<List<Offer>> offersFileDataReader;
    private final LoanCalculator calculator;

    @Autowired
    public LoanCalculationCommand(ArgumentResolver argumentResolver, FileDataReader<List<Offer>> offersFileDataReader,
                                  LoanCalculator calculator) {

        this.argumentResolver = argumentResolver;
        this.offersFileDataReader = offersFileDataReader;
        this.calculator = calculator;
    }

    @Override
    public void execute(String[] args) {
        try {
            doExecute(args);
        } catch (Exception e) {
            System.out.println(String.format("Oh no! \\(°o°)/ %s", e.getMessage()));
        }
    }

    private void doExecute(String args[]) {
        LoanCalculationArguments arguments = argumentResolver.resolve(args);
        List<Offer> offers = offersFileDataReader.read(arguments.getFilename());
        Optional<Loan> loan = calculator.calculate(arguments.getAmount(), offers, LOAN_PERIOD_MONTHS);

        if (!loan.isPresent()) {
            System.out.println(MESSAGE_CANT_SATISFY_REQUEST);
            return;
        }

        Loan result = loan.get();

        System.out.println(String.format("%s - %s - %s - %s", result.getRequestedAmount(), result.getRate(),
                result.getMonthlyRepayment(), result.getTotalRepayment()));
    }
}
