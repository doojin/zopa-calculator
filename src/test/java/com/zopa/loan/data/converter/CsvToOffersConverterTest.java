package com.zopa.loan.data.converter;

import com.zopa.loan.model.Offer;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class CsvToOffersConverterTest extends CsvConverterTest {

    @Mock
    private Converter<CSVRecord, Offer> rowConverter;

    @InjectMocks
    private CsvToOffersConverter converter;

    @Test
    public void convert_whenRowsAreNull_shouldReturnEmptyList() {
        List<Offer> offers = converter.convert(null);
        assertThat(offers.size(), equalTo(0));
    }

    @Test
    public void convert_whenRowsAreEmptyList_shouldReturnEmptyList() {
        List<Offer> offers = converter.convert(emptyList());
        assertThat(offers.size(), equalTo(0));
    }

    @Test
    public void convert_whenRowsContainOnlyHeaderRow_shouldReturnEmptyList() throws IOException {
        List<CSVRecord> rows = singletonList(row("Header1", "Header2", "Header3"));

        List<Offer> offers = converter.convert(rows);

        assertThat(offers.size(), equalTo(0));
        verify(rowConverter, never()).convert(any(CSVRecord.class));
    }

    @Test
    public void convert_whenRowsContainHeaderAndDataRow_shouldReturnListOfOneOffer() throws IOException {
        CSVRecord headerRow = row("Header1", "Header2", "Header3");
        CSVRecord dataRow = row("data1", "data2", "data3");
        List<CSVRecord> rows = asList(headerRow, dataRow);

        Offer offer = new Offer(0.1, 1000);
        doReturn(offer).when(rowConverter).convert(dataRow);

        List<Offer> offers = converter.convert(rows);

        assertThat(offers.size(), equalTo(1));
        assertThat(offers.get(0), equalTo(offer));

        verify(rowConverter, times(1)).convert(dataRow);
        verify(rowConverter, never()).convert(headerRow);
    }

    @Test
    public void convert_whenRowsContainDataRows_shouldReturnListOfConvertedOffers() throws IOException {
        CSVRecord headerRow = row("Header1", "Header2", "Header3");
        CSVRecord dataRow1 = row("r1c1", "r1c2", "r1c3");
        CSVRecord dataRow2 = row("r2c1", "r2c2", "r2c3");

        List<CSVRecord> rows = asList(headerRow, dataRow1, dataRow2);

        Offer offer1 = new Offer(0.1, 1000);
        doReturn(offer1).when(rowConverter).convert(dataRow1);

        Offer offer2 = new Offer(0.2, 2000);
        doReturn(offer2).when(rowConverter).convert(dataRow2);

        List<Offer> offers = converter.convert(rows);

        assertThat(offers.size(), equalTo(2));
        assertTrue(offers.contains(offer1));
        assertTrue(offers.contains(offer2));

        verify(rowConverter, times(1)).convert(dataRow1);
        verify(rowConverter, times(1)).convert(dataRow2);
        verify(rowConverter, never()).convert(headerRow);
    }

}