package com.zopa.loan.command.printer;

import com.zopa.loan.model.Loan;
import com.zopa.loan.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class InformationPrinterImpl implements InformationPrinter {

    private static final DecimalFormat FORMAT_REQUESTED_AMOUNT = new DecimalFormat("£#");
    private static final DecimalFormat FORMAT_RATE = new DecimalFormat("#0.0%");
    private static final DecimalFormat FORMAT_REPAYMENT = new DecimalFormat("£#0.00");

    private static final String FORMAT_ERROR_MESSAGE = "Oh no! \\(°o°)/ %s";

    private final MessageSender sender;

    @Autowired
    public InformationPrinterImpl(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void printLoan(Loan loan) {
        sender.send(String.format("Requested amount: %s", FORMAT_REQUESTED_AMOUNT.format(loan.getRequestedAmount())));
        sender.send(String.format("Rate: %s", FORMAT_RATE.format(loan.getRate())));
        sender.send(String.format("Monthly repayment: %s", FORMAT_REPAYMENT.format(loan.getMonthlyRepayment())));
        sender.send(String.format("Total repayment: %s", FORMAT_REPAYMENT.format(loan.getTotalRepayment())));
    }

    @Override
    public void printInfoMessage(String message) {
        sender.send(message);
    }

    @Override
    public void printErrorMessage(Exception e) {
        sender.send(String.format(FORMAT_ERROR_MESSAGE, e.getMessage()));
    }
}
