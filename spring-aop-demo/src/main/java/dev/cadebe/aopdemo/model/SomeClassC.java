package dev.cadebe.aopdemo.model;

import org.springframework.stereotype.Component;

@Component
public class SomeClassC {

    public String convertToLowerCase(String input) {
        return "Converting the input string to lowercase " + input.toLowerCase();
    }

    public String convertToUppercaseCase(String input) {
        return "Converting the input string to uppercase " + input.toUpperCase();
    }
}
