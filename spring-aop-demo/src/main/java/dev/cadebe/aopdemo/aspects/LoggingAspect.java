package dev.cadebe.aopdemo.aspects;

import dev.cadebe.aopdemo.exceptions.CalculationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* dev.cadebe.aopdemo.model.SomeClassA.convertToLowerCase*(..))") // Separate pointcut
    public void doSomethingWithClassA() {
    }

    @Pointcut("execution(* dev.cadebe..SomeClassB.convertTo*(..))") // Separate pointcut
    public void doSomeThingWithClassB() {
    }

    @Pointcut(value = "execution(* *.*.*(..))") // Too generic to match
    public void matchingAll() {
    }

    // Advice => logBefore(String input)
    @Before("doSomethingWithClassA() && args(input)") // Pointcut expression
    public String interceptBeforeExecutionAndArgs(String input) {
        log.info("---> 1. interceptBeforeExecutionAndArgs");
        log.info("args(input): {}", input);

        return "Aspect @Before has been called with input: " + input;
    }

    // Advice => logAfter(String input)
    @After("doSomethingWithClassA() && args(input)")  // Pointcut expression
    public String interceptAfterExecutionAndArgs(String input) {
        log.info("---> 2. interceptAfterExecutionAndArgs");
        log.info("args(input): {}", input);

        return "Aspect @After has been called with input: " + input;
    }

    @After("within(dev.cadebe.aopdemo.model.SomeClassC)")  // Pointcut expression
    public String interceptAfterAllSomeClassCMethods() {
        log.info("---> 3. interceptAfterExecutionAndArgs");

        return "Aspect @After has been called for all methods called for SomeClassC";
    }

    @Before("@annotation(dev.cadebe.aopdemo.annotations.NumberCalculation)") // Inline pointcut expression
    public String interceptAnnotation() {
        log.info("---> 4. interceptAnnotation");

        return "Aspect @Before has been called for annotation NumberCalculation";
    }

    // NOTE: if the @Around Advice is used, @Before and @After will NOT be called for the same pointcut
    @Around("doSomeThingWithClassB()")
    public String interceptAround(ProceedingJoinPoint pjp) {
        log.info("---> 5. logAround");
        log.info("ProceedingJoinPoint.getSignature(): {}", pjp.getSignature());
        log.info("ProceedingJoinPoint.getTarget(): {}, ProceedingJoinPoint.getThis(): {}, ProceedingJoinPoint.getKind(): {}", pjp.getTarget(), pjp.getThis(), pjp.getKind());

        try {
            pjp.proceed();
        } catch (Throwable e) {
            log.info("Exception thrown on proceeding {}", e.getMessage());
        }

        return "Aspect @Around has been called";
    }

    @AfterReturning(value = "execution(* dev.cadebe.aopdemo.model.SomeClassA.returnNumberSquared(*))", returning = "result")
    public String interceptAfterReturning(double result) {
        log.info("---> 6. Logging the result of the value squared: {}", result);

        return "Number squared = " + Math.round(result);
    }

    @AfterThrowing(value = "execution(* dev.cadebe.aopdemo.model.SomeClassA.calculateSomething(*))", throwing = "e")
    public String interceptThrowingArithmeticException(ArithmeticException e) {
        log.info("---> 7. afterThrowing ArithmeticException {}", e.getMessage());

        throw new CalculationException("Zero or empty as invalid input");
    }

    @AfterThrowing(value = "execution(* dev.cadebe.aopdemo.model.SomeClassA.calculateSomething(..))", throwing = "e")
    public String interceptThrowingNumberFormatException(NumberFormatException e) {
        log.info("---> 8. afterThrowing NumberFormatException {}", e.getMessage());

        throw new CalculationException("Incorrect numeric format");
    }
}
