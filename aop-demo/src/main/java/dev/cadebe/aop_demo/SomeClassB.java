package dev.cadebe.aop_demo;

public class SomeClassB {

    // Joint point method
    public String doSomeThing(String input) {
        System.out.println("Calling SomeClass.doSomeThing()");
        return "I am doing something with " + input + " and returning some string value";
    }
}
