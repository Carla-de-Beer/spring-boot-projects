package dev.cadebe.aop_demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LoggingAspectTest {

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Test
    void testAOPWithSomeClassA() {
        SomeClassA target = new SomeClassA();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassA proxy = factory.getProxy();

        String input = "input A";

        String result = proxy.doSomeThing(input);

        assertThat(result).isEqualTo("I am doing something with input A and returning some string value");

        assertEquals("Aspect @Before has been called with input: input A", aspect.logBefore(input));
        assertEquals("Aspect @After has been called with input: input A", aspect.logAfter(input));
    }

    @Test
    void testAOPWithSomeClassB() {
        SomeClassB target = new SomeClassB();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassB proxy = factory.getProxy();

        String result = proxy.doSomeThing("input B");

        assertThat(result).isEqualTo("Aspect @Around has been called");
        assertEquals("Aspect @Around has been called", aspect.logBeforeAndAfter(proceedingJoinPoint));
    }

    @Test
    void testAOPWithSomeClassAWithExceptionHandling() {
        SomeClassA target = new SomeClassA();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);
        SomeClassA proxy = factory.getProxy();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> proxy.calculateSomething(""));

        System.out.println(exception.getMessage());

        assertEquals("Exception thrown", aspect.afterThrowing());
    }
}