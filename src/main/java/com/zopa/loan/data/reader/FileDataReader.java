package com.zopa.loan.data.reader;

public interface FileDataReader<T> {

    T read(String filename);
}
