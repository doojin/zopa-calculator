package com.zopa.loan.command;

public class LoanCalculationArguments {

    private final String filename;
    private final long amount;

    public LoanCalculationArguments(String filename, long amount) {
        this.filename = filename;
        this.amount = amount;
    }

    public String getFilename() {
        return filename;
    }

    public long getAmount() {
        return amount;
    }
}
