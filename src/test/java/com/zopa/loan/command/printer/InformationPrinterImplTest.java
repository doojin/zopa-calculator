package com.zopa.loan.command.printer;

import com.zopa.loan.model.Loan;
import com.zopa.loan.sender.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InformationPrinterImplTest {

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private InformationPrinterImpl printer;

    @Test
    public void printLoan_shouldPrintFormattedLoanValues() {
        Loan loan = new Loan(1000.12345, 0.12345, 10.12345, 1100);

        printer.printLoan(loan);

        verify(messageSender, times(1)).send("Requested amount: £1000");
        verify(messageSender, times(1)).send("Rate: 12.3%");
        verify(messageSender, times(1)).send("Monthly repayment: £10.12");
        verify(messageSender, times(1)).send("Total repayment: £1100.00");
    }

    @Test
    public void printErrorMessage_shouldSendFormattedErrorMessage() {
        Exception exception = new RuntimeException("test error message");

        printer.printErrorMessage(exception);

        verify(messageSender, times(1)).send("Oh no! \\(°o°)/ test error message");
    }
}