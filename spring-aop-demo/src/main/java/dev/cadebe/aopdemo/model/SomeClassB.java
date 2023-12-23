package dev.cadebe.aopdemo.model;

import dev.cadebe.aopdemo.annotations.NumberCalculation;
import org.springframework.stereotype.Component;

@Component
public class SomeClassB {

    // Joint point method
    public String convertToUpperCase(String input) {
        return "Converting the input string to uppercase " + input.toUpperCase();
    }

    // Joint point method
    @NumberCalculation
    public double returnNumberHalved(double input) {
        return input * 0.5;
    }
}
