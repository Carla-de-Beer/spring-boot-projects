package dev.cadebe.aopdemo;

import dev.cadebe.aopdemo.exceptions.CalculationException;
import dev.cadebe.aopdemo.model.SomeClassA;
import dev.cadebe.aopdemo.model.SomeClassB;
import dev.cadebe.aopdemo.model.SomeClassC;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class AopLoggingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AopLoggingApplication.class, args);

        val classA = context.getBean(SomeClassA.class);
        log.info("classA: convertToLowerCase() {}", classA.convertToLowerCase("ASDFG"));
        log.info("classA: returnNumberSquared() {}", classA.returnNumberSquared(12));
        log.info("classA: calculateSomething() with zero {}", classA.calculateSomething("42.0"));

        try {
            log.info("classA: calculateSomething() with zero {}", classA.calculateSomething("0.0"));
        } catch (CalculationException e) {
            log.info("classA: CalculationException thrown with invalid String");
        }

        try {
            log.info("classA: calculateSomething() with invalid number format {}", classA.calculateSomething("XXX"));
        } catch (CalculationException e) {
            log.info("classA: CalculationException thrown with invalid String");
        }

        try {
            log.info("classA: calculateSomething() with valid number format {}", classA.calculateSomething(""));
        } catch (CalculationException e) {
            log.info("classA: CalculationException thrown with empty String");
        }

        val classB = context.getBean(SomeClassB.class);
        log.info("classB: convertToUpperCase() {}", classB.convertToUpperCase("asdfg"));
        log.info("classB: returnNumberHalved() {}", classB.returnNumberHalved(200));

        val classC = context.getBean(SomeClassC.class);
        log.info("classC: convertToLowerCase() {}", classC.convertToLowerCase("LKJHG"));
        log.info("classC: convertToUppercaseCase() {}", classC.convertToUppercaseCase("poiuy"));
    }
}