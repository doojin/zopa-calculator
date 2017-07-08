package com.zopa.loan.data.parser;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CsvDataParserTest {

    private static final String FILE_OFFERS = "csv/offers.csv";
    private static final String FILE_ROWS_WITH_DIFFERENT_SIZES = "csv/rows_different_size.csv";

    private CsvDataParser parser;

    @Before
    public void setUp() {
        parser = new CsvDataParser();
    }

    @Test
    public void parse_shouldReturnListOfCSVRecords() throws IOException {
        InputStream is = getFileInputStream(FILE_OFFERS);

        List<CSVRecord> rows = parser.parse(is);

        assertThat(rows.size(), equalTo(8));
        assertRow(rows.get(0), 3, "Lender", "Rate", "Available");
        assertRow(rows.get(1), 3, "Bob", "0.075", "640");
        assertRow(rows.get(2), 3, "Jane", "0.069", "480");
        assertRow(rows.get(3), 3, "Fred", "0.071", "520");
        assertRow(rows.get(4), 3, "Mary", "0.104", "170");
        assertRow(rows.get(5), 3, "John", "0.081", "320");
        assertRow(rows.get(6), 3, "Dave", "0.074", "140");
        assertRow(rows.get(7), 3, "Angela", "0.071", "60");
    }

    @Test
    public void parse_shouldParseProperlyRowsOfDifferentSizes() throws IOException {
        InputStream is = getFileInputStream(FILE_ROWS_WITH_DIFFERENT_SIZES);

        List<CSVRecord> rows = parser.parse(is);

        assertThat(rows.size(), equalTo(5));
        assertRow(rows.get(0), 3, "Column1", "Column2", "Column3");
        assertRow(rows.get(1), 3, "r1c1", "r1c2", "r1c3");
        assertRow(rows.get(2), 2, "r2c1", "r2c2");
        assertRow(rows.get(3), 1, "r3c1");
        assertRow(rows.get(4), 3, "r4c1", "r4c2", "r4c3");
    }

    private static void assertRow(CSVRecord row, int expectedRowSize, String... values) {
        assertThat(row.size(), equalTo(expectedRowSize));

        for (int i = 0; i < expectedRowSize; i++) {
            assertThat(row.get(i), equalTo(values[i]));
        }
    }

    private InputStream getFileInputStream(String filename) {
        return this.getClass().getClassLoader().getResourceAsStream(filename);
    }

}