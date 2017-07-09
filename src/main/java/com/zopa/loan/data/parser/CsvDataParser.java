package com.zopa.loan.data.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class CsvDataParser implements DataParser<List<CSVRecord>> {

    private static final String ERROR_INVALID_CSV_FILE = "Failed to parse your CSV file. Are you sure it's a valid CSV";

    @Override
    public List<CSVRecord> parse(InputStream is)  {
        try (Reader reader = new InputStreamReader(is)) {
            return new CSVParser(reader, CSVFormat.DEFAULT).getRecords();
        } catch (IOException e) {
            throw new IllegalStateException(ERROR_INVALID_CSV_FILE, e);
        }
    }
}
