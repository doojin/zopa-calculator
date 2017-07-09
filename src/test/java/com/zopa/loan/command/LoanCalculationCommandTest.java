package com.zopa.loan.command;

import com.zopa.loan.calculator.LoanCalculator;
import com.zopa.loan.command.printer.InformationPrinter;
import com.zopa.loan.command.resolver.ArgumentResolver;
import com.zopa.loan.data.reader.FileDataReader;
import com.zopa.loan.model.Loan;
import com.zopa.loan.model.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanCalculationCommandTest {

    @Mock private ArgumentResolver argumentResolver;
    @Mock private FileDataReader<List<Offer>> offersFileDataReader;
    @Mock private LoanCalculator calculator;
    @Mock private InformationPrinter printer;

    @InjectMocks
    private LoanCalculationCommand command;

    @Test
    public void execute_whenExceptionIsThrown_shouldPrintErrorMessage() {
        Exception exception = new RuntimeException();

        String[] args = new String[]{};
        doThrow(exception).when(argumentResolver).resolve(args);

        command.execute(args);

        verify(printer, times(1)).printErrorMessage(exception);
    }

    @Test
    public void execute_whenLoanCannotBeCalculated_shouldPrintInfoMessage() {
        String[] args = new String[]{};

        LoanCalculationArguments arguments = new LoanCalculationArguments("test-filename.csv", 1000);
        doReturn(arguments).when(argumentResolver).resolve(args);

        List<Offer> offers = emptyList();
        doReturn(offers).when(offersFileDataReader).read("test-filename.csv");

        doReturn(Optional.empty()).when(calculator).calculate(1000, offers, 36);

        command.execute(args);

        verify(printer, times(1))
                .printInfoMessage("We are not able fully satisfy your request. You can try with a lower amount.");

    }

    @Test
    public void execute_whenLoanCanBeCalculated_shouldPrintLoan() {
        String[] args = new String[]{};

        LoanCalculationArguments arguments = new LoanCalculationArguments("test-filename.csv", 1000);
        doReturn(arguments).when(argumentResolver).resolve(args);

        List<Offer> offers = emptyList();
        doReturn(offers).when(offersFileDataReader).read("test-filename.csv");

        Loan loan = new Loan(1, 1, 1, 1);
        doReturn(Optional.of(loan)).when(calculator).calculate(1000, offers, 36);

        command.execute(args);

        verify(printer, times(1)).printLoan(loan);
    }
}