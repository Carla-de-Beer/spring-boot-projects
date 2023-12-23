package dev.cadebe.aopdemo;

import dev.cadebe.aopdemo.aspects.LoggingAspect;
import dev.cadebe.aopdemo.exceptions.CalculationException;
import dev.cadebe.aopdemo.model.SomeClassA;
import dev.cadebe.aopdemo.model.SomeClassB;
import dev.cadebe.aopdemo.model.SomeClassC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LoggingAspectTest {

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Test
    void shouldInterceptClassAOnMethodExecutionAndArgs() {
        SomeClassA target = new SomeClassA();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassA proxy = factory.getProxy();

        String input = "INPUT A";

        String result = proxy.convertToLowerCase(input);

        assertThat(result).isEqualTo("Converting the input string to lowercase input a");
        assertEquals("Aspect @Before has been called with input: INPUT A", aspect.interceptBeforeExecutionAndArgs(input));
        assertEquals("Aspect @After has been called with input: INPUT A", aspect.interceptAfterExecutionAndArgs(input));
    }

    @Test
    void shouldInterceptClassBOnMethodExecutionAndArgs() {
        SomeClassB target = new SomeClassB();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassB proxy = factory.getProxy();

        String result = proxy.convertToUpperCase("input B");

        assertThat(result).isEqualTo("Aspect @Around has been called");
        assertEquals("Aspect @Around has been called", aspect.interceptAround(proceedingJoinPoint));
    }

    @Test
    void shouldInterceptAfterAllSomeClassBMethods() {
        SomeClassC target = new SomeClassC();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassC proxy = factory.getProxy();

        String result1 = proxy.convertToUppercaseCase("QweRtY");
        String result2 = proxy.convertToLowerCase("YTreWq");

        assertThat(result1).isEqualTo("Converting the input string to uppercase QWERTY");
        assertThat(result2).isEqualTo("Converting the input string to lowercase ytrewq");
        assertEquals("Aspect @After has been called for all methods called for SomeClassC", aspect.interceptAfterAllSomeClassCMethods());
    }

    @Test
    void shouldInterceptAnnotation() {
        SomeClassB target = new SomeClassB();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassB proxy = factory.getProxy();

        double result = proxy.returnNumberHalved(42);

        assertThat(result).isEqualTo(21.0);
        assertEquals("Aspect @Before has been called for annotation NumberCalculation", aspect.interceptAnnotation());
    }

    @Test
    void shouldInterceptClassAOnMethodReturn() {
        SomeClassA target = new SomeClassA();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassA proxy = factory.getProxy();

        double result = proxy.returnNumberSquared(5.0);

        assertThat(result).isEqualTo(25);
        assertEquals("Number squared = 25", aspect.interceptAfterReturning(25));
    }

    @Test
    void shouldNotCatchOrInterceptOrConvertExceptionsToCalculationException() {
        SomeClassA target = new SomeClassA();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassA proxy = factory.getProxy();

        double result = proxy.calculateSomething("100.25");

        assertThat(result).isEqualTo(100.25);
    }

    static Stream<Arguments> shouldCatchAndInterceptAndConvertExceptionsToCalculationException() {
        return Stream.of(
                Arguments.arguments("0.00", "Zero or empty as invalid input"),
                Arguments.arguments("", "Zero or empty as invalid input"),
                Arguments.arguments(" ", "Zero or empty as invalid input"),
                Arguments.arguments("XXX", "Incorrect numeric format")

        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldCatchAndInterceptAndConvertExceptionsToCalculationException(String input, String message) {
        SomeClassA target = new SomeClassA();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassA proxy = factory.getProxy();

        RuntimeException exception = assertThrows(CalculationException.class, () -> proxy.calculateSomething(input));

        assertThat(exception.getMessage()).isEqualTo(message);
    }
}