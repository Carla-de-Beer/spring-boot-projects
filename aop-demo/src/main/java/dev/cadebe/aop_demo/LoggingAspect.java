package dev.cadebe.aop_demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* dev.cadebe.aop_demo.SomeClassA.doSomeThing(..))")
    public void doSomethingWithClassA() {
    }

    @Pointcut("execution(* dev.cadebe.aop_demo.SomeClassB.doSomeThing(..))")
    public void doSomeThingWithClassB() {
    }

    @Pointcut(value = "execution(* *.*.*(..))")
    public void matchingAll() {
    }

    // Advice => logBefore(String input)
    @Before("doSomethingWithClassA() && args(input)") // Pointcut expression
    public String logBefore(String input) {
        log.info("***** logBefore *****");
        log.info("args(input): " + input);

        return "Aspect @Before has been called with input: " + input;
    }

    // Advice => logAfter(String input)
    @After("doSomethingWithClassA() && args(input)")  // Pointcut expression
    public String logAfter(String input) {
        log.info("***** logAfter *****");
        log.info("args(input): " + input);

        return "Aspect @After has been called with input: " + input;
    }

    // NOTE: if the @Around Advice is used, @Before and @After will NOT be called for the same pointcut
    @Around("doSomeThingWithClassB()")
    public String logBeforeAndAfter(ProceedingJoinPoint pjp) {
        log.info("***** logBeforeAndAfter *****");
        log.info("ProceedingJoinPoint.getSignature(): " + pjp.getSignature());
        if (pjp.getArgs() != null) {
            log.info("ProceedingJoinPoint.getArgs().length: " + pjp.getArgs().length);
        }
        log.info("ProceedingJoinPoint.getTarget(): " + pjp.getTarget());
        log.info("ProceedingJoinPoint.getThis(): " + pjp.getThis());
        log.info("ProceedingJoinPoint.getKind(): " + pjp.getKind());

        return "Aspect @Around has been called";
    }

    @AfterThrowing(value = "matchingAll()")
    public String afterThrowing() {
        log.info("***** afterThrowing *****");

        return "Exception thrown";
    }
}
