package dev.cadebe.aopdemo.model;

import org.springframework.stereotype.Component;

@Component
public class SomeClassA {

    // Join point method
    public String convertToLowerCase(String input) {
        return "Converting the input string to lowercase " + input.toLowerCase();
    }

    // Joint point method
    public double returnNumberSquared(double input) {
        return Math.pow(input, 2);
    }

    // Joint point method
    public double calculateSomething(String input) {
        if (input.isEmpty() || input.isBlank() || Double.parseDouble(input) == 0) {
            throw new ArithmeticException("Zero or empty not allowed as input");
        }

        return Double.parseDouble(input);
    }
}
