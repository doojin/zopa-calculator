package com.zopa.loan;

import com.zopa.loan.command.Command;
import com.zopa.loan.command.LoanCalculationCommand;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Command command = context.getBean(LoanCalculationCommand.class);
        command.execute(args);
    }
}
