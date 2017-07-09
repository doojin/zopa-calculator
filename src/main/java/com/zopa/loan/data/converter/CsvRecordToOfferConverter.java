package com.zopa.loan.data.converter;

import com.zopa.loan.model.Offer;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.lang.Double.parseDouble;

@Component
public class CsvRecordToOfferConverter implements Converter<CSVRecord, Offer> {

    private static final int ROW_SIZE = 3;
    private static final int INDEX_RATE = 1;
    private static final int INDEX_AMOUNT = 2;

    private static final String ERROR_INVALID_CSV_FORMAT =
            "Your CSV data contains an invalid row. Expecting all rows to have 3 columns.";

    private static final String ERROR_INVALID_DECIMAL =
            "Your data contains an invalid %s: %s. Expecting a valid decimal value";

    @Override
    public Offer convert(CSVRecord row) {
        if (!isValidSize(row)) {
            throw new IllegalStateException(ERROR_INVALID_CSV_FORMAT);
        }

        return new Offer(getRate(row), getAmount(row));
    }

    private boolean isValidSize(CSVRecord row) {
        return ROW_SIZE == row.size();
    }

    private double getRate(CSVRecord row) {
        return parseDecimalValue(row, INDEX_RATE, "rate");
    }

    private double getAmount(CSVRecord row) {
        return parseDecimalValue(row, INDEX_AMOUNT, "amount");
    }

    private double parseDecimalValue(CSVRecord row, int index, String fieldName) {
        String value = row.get(index);

        try {
            return parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalStateException(String.format(ERROR_INVALID_DECIMAL, fieldName, value), e);
        }
    }
}
