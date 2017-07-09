package com.zopa.loan.command.printer;

import com.zopa.loan.model.Loan;

public interface InformationPrinter {

    void printLoan(Loan loan);
    void printInfoMessage(String message);
    void printErrorMessage(Exception e);
}
