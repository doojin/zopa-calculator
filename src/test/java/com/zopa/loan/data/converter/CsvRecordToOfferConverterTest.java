package com.zopa.loan.data.converter;

import com.zopa.loan.model.Offer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CsvRecordToOfferConverterTest {

    private static final String FAILURE_EXCEPTION_EXPECTED = "Expecting exception to be thrown but it wasn't";

    private CsvRecordToOfferConverter converter;

    @Before
    public void setUp() {
        converter = new CsvRecordToOfferConverter();
    }

    @Test
    public void convert_whenInvalidColumnCount_shouldThrowException() throws IOException {
        try {
            converter.convert(row("column1", "column2"));
            fail(FAILURE_EXCEPTION_EXPECTED);
        } catch (Exception e) {
            String expectedMessage = "Your CSV data contains an invalid row. Expecting all rows to have 3 columns.";
            assertThat(e.getMessage(), equalTo(expectedMessage));
        }
    }

    @Test
    public void convert_whenRowContainsInvalidRate_shouldThrowException() throws IOException {
        try {
            converter.convert(row("test-lender", "invalid-rate", "1000"));
            fail(FAILURE_EXCEPTION_EXPECTED);
        } catch (Exception e) {
            String expectedMessage = "Your data contains an invalid rate: invalid-rate. Expecting a valid decimal value";
            assertThat(e.getMessage(), equalTo(expectedMessage));
        }
    }

    @Test
    public void convert_whenRowContainsInvalidAmount_shouldThrowException() throws IOException {
        try {
            converter.convert(row("test-lender", "7.5", "invalid-amount"));
            fail(FAILURE_EXCEPTION_EXPECTED);
        } catch (Exception e) {
            String expectedMessage = "Your data contains an invalid amount: invalid-amount. Expecting a valid decimal value";
            assertThat(e.getMessage(), equalTo(expectedMessage));
        }
    }

    @Test
    public void convert_whenValidRateAndAmount_shouldReturnOffer() throws IOException {
        Offer offer = converter.convert(row("test-lender", "7.5", "100"));

        assertThat(offer.getRate(), equalTo(7.5D));
        assertThat(offer.getAmount(), equalTo(100D));
    }

    @Test
    public void convert_whenRowContainsNegativeAmount_shouldNotThrowException() throws IOException {
        Offer offer = converter.convert(row("test-lender", "7.5", "-500"));

        assertThat(offer.getRate(), equalTo(7.5D));
        assertThat(offer.getAmount(), equalTo(-500D));
    }

    private CSVRecord row(String... values) throws IOException {
        return CSVParser.parse(String.join(",", values), CSVFormat.DEFAULT).getRecords().get(0);
    }

}