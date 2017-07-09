package com.zopa.loan.data.reader;

import java.io.IOException;

public interface FileDataReader<T> {

    T read(String filename) throws IOException;
}
