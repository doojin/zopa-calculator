package com.zopa.loan.data.reader;

import com.zopa.loan.data.parser.DataParser;
import com.zopa.loan.model.Offer;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class OfferDataReader implements FileDataReader<List<Offer>> {

    private final DataParser<List<CSVRecord>> csvParser;
    private final Converter<List<CSVRecord>, List<Offer>> converter;

    @Autowired
    public OfferDataReader(DataParser<List<CSVRecord>> csvParser, Converter<List<CSVRecord>, List<Offer>> converter) {
        this.csvParser = csvParser;
        this.converter = converter;
    }

    @Override
    public List<Offer> read(String filename) throws IOException {
        InputStream is = getInputStream(filename);
        List<CSVRecord> rows = csvParser.parse(is);
        return converter.convert(rows);
    }

    private InputStream getInputStream(String filename) {
        File file = new File(filename);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(String.format("Your CSV data file: '%s' was not found", filename), e);
        }
    }
}
