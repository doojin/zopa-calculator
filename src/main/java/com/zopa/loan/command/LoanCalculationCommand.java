package com.zopa.loan.command;

import com.zopa.loan.calculator.LoanCalculator;
import com.zopa.loan.command.printer.InformationPrinter;
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
            "We are not able fully satisfy your request. You can try with a lower amount.";

    private static final int LOAN_PERIOD_MONTHS = 36;

    private final ArgumentResolver argumentResolver;
    private final FileDataReader<List<Offer>> offersFileDataReader;
    private final LoanCalculator calculator;
    private final InformationPrinter printer;

    @Autowired
    public LoanCalculationCommand(ArgumentResolver argumentResolver, FileDataReader<List<Offer>> offersFileDataReader,
                                  LoanCalculator calculator, InformationPrinter printer) {

        this.argumentResolver = argumentResolver;
        this.offersFileDataReader = offersFileDataReader;
        this.calculator = calculator;
        this.printer = printer;
    }

    @Override
    public void execute(String[] args) {
        try {
            doExecute(args);
        }
        catch (Exception e) {
            printer.printErrorMessage(e);
        }
    }

    private void doExecute(String args[]) {
        LoanCalculationArguments arguments = argumentResolver.resolve(args);

        List<Offer> offers = offersFileDataReader.read(arguments.getFilename());
        Optional<Loan> loan = calculator.calculate(arguments.getAmount(), offers, LOAN_PERIOD_MONTHS);

        if (!loan.isPresent()) {
            printer.printInfoMessage(MESSAGE_CANT_SATISFY_REQUEST);
            return;
        }

        printer.printLoan(loan.get());
    }
}
