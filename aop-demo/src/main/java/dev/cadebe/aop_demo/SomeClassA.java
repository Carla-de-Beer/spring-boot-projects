package dev.cadebe.aop_demo;

import org.springframework.stereotype.Component;

@Component
public class SomeClassA {

    // Join point method
    public String doSomeThing(String input) {
        return "I am doing something with " + input + " and returning some string value";
    }

    public double calculateSomething(String input) {
        try {
            return 100 / Double.parseDouble(input);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new RuntimeException("Exception message: " + e.getMessage());
        }
    }
}
