package com.zopa.loan.data.parser;

import java.io.IOException;
import java.io.InputStream;

public interface DataParser<T> {

    T parse(InputStream fileInputStream) throws IOException;
}
