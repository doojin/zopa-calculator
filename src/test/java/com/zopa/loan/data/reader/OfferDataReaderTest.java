package com.zopa.loan.data.reader;

import com.zopa.loan.data.parser.DataParser;
import com.zopa.loan.model.Offer;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class OfferDataReaderTest {

    @Mock private DataParser<List<CSVRecord>> csvParser;
    @Mock private Converter<List<CSVRecord>, List<Offer>> converter;

    @InjectMocks
    private OfferDataReader reader;

    @Test
    public void read_whenFileNotExists_shouldThrowException() throws IOException {
        try {
            reader.read("nonexistent-file.csv");
            fail("Expected exception but wasn't thrown");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("File 'nonexistent-file.csv' was not found"));
        }
    }

    @Test
    public void read_whenFileExists_shouldNotThrowException() throws IOException {
        List<Offer> offers = singletonList(new Offer(0.1, 100));

        doReturn(emptyList()).when(csvParser).parse(any(InputStream.class));
        doReturn(offers).when(converter).convert(emptyList());

        List<Offer> result = reader.read(getExistingFilename());

        assertThat(result, equalTo(offers));
    }

    private String getExistingFilename() {
        return this.getClass().getClassLoader().getResource("csv/offers.csv").getFile();
    }

}