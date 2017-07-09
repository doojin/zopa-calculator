package com.zopa.loan.data.converter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public abstract class CsvConverterTest {

    protected CSVRecord row(String... values) throws IOException {
        return CSVParser.parse(String.join(",", values), CSVFormat.DEFAULT).getRecords().get(0);
    }
}
