package com.zopa.loan.command.resolver;

import com.zopa.loan.command.LoanCalculationArguments;
import com.zopa.loan.command.parser.ArgumentParser;
import com.zopa.loan.command.validator.ArgumentValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ArgumentResolverTest {

    @Mock private ArgumentParser parser;
    @Mock private ArgumentValidator validator;

    @InjectMocks
    private ArgumentResolver resolver;

    private LoanCalculationArguments parsedArguments = new LoanCalculationArguments("test-filename.csv", 1234);

    @Before
    public void setUp() {
        doReturn(parsedArguments).when(parser).parse(anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_whenValidatorReturnsError_shouldThrowException() {
        doReturn(Optional.of("test error")).when(validator).getValidationError(parsedArguments);
        resolver.resolve(new String[]{});
    }

    @Test
    public void resolve_whenValidatorNotReturnsError_shouldReturnParsedArguments() {
        doReturn(Optional.empty()).when(validator).getValidationError(parsedArguments);

        LoanCalculationArguments resolvedArguments = resolver.resolve(new String[]{});

        assertThat(resolvedArguments, equalTo(parsedArguments));
    }
}