# Spring AOP Example

This is a simple Spring Boot demo project demonstrating Aspect Oriented Programming (AOP) functionalities.

The project is built with Java 11 and Maven. 

## AOP Terminology simply explained

AOP is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. 
It does so by adding additional behaviour to existing code without modification of the code itself. 
With AOP, you just decorate the needed behaviour without touching the code base, 
thereby allowing you to intercept methods that may have been written by third parties. 
In other words, Spring AOP provides a non-intrusive way of altering our components, even if we don't own the code for that component.

* **Join Point**: In Spring this always represents a method execution, i.e. the method you wish to intercept.
* **Pointcut**: A regular expression that matches the join point, i.e. it is a predicate that matches an advice at a particular join point.
* **Advice**: Once Pointcuts are defined, we need to decide what to do with them and for that we use methods that are called *advices*. An advice is an action taken by an aspect aat a particular join point. There are different types of advices:
    * **@Before**: Advice will be called before Join point.
    * **@After**: Advice will be called after Join point, regardless it has throw an exception or not.
    * **@Around**: This kind of advice can be invoked before and after join point method is called.
    * **@AfterReturning**: Advice will be called after join point, unless it will throw an exception.
    * **@AfterThrowing**: Advice will be called after join point, but only then when it will throw an exception.
* **Aspect**: A place where several Pointcuts are coupled with their advices is called an aspect. 
It is also the location of the new logic you want to add to the existing class, method, classes or methods. 
The aspect is the modularisation of a concern that cuts across multiple classes and/or methods.
* **AOP proxy**: An object created by the AOP framework in order to implement the aspect contracts (advise method executions and so on). In the Spring Framework, an AOP proxy will be a JDK dynamic proxy or a CGLIB proxy.
* **Weaving**: The Weaver is the framework that implements AOP — AspectJ or Spring AOP. Weaving links aspects with other application types or objects to create an advised object. 
The weaver scans the components in the `ApplicationContext` and dynamically generates code behind the scenes.

## Resources

* https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-api
* https://medium.com/@wkrzywiec/moving-into-next-level-in-user-log-events-with-spring-aop-3b4435892f16

