package com.zopa.loan.data.converter;

import com.zopa.loan.model.Offer;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.size;

@Component
public class CsvToOffersConverter implements Converter<List<CSVRecord>, List<Offer>> {

    private final Converter<CSVRecord, Offer> rowConverter;

    @Autowired
    public CsvToOffersConverter(Converter<CSVRecord, Offer> rowConverter) {
        this.rowConverter = rowConverter;
    }

    @Override
    public List<Offer> convert(List<CSVRecord> csvRecords) {
        if (size(csvRecords) < 2) return emptyList();

        return csvRecords.subList(1, csvRecords.size()).stream()
                .map(rowConverter::convert)
                .collect(toList());
    }
}
