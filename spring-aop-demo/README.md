# Spring AOP Example

This is a simple Spring Boot project demonstrating Aspect Oriented Programming (AOP) functionalities.

The project is built with Java 11 and Maven.

## AOP Terminology simply explained

AOP is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. It
does so by adding additional behaviour to existing code without modification of the code itself. With AOP, you just
decorate the needed behaviour without touching the code base, thereby allowing you to intercept methods that may have
been written by third parties. In other words, Spring AOP provides a non-intrusive way of altering components, even
if we don't own the code for that component.

* **Join Point**: In Spring this always represents a method execution, i.e. the method you wish to intercept.
* **Pointcut**: A regular expression that matches the join point, i.e. it is a predicate that matches an advice at a
  particular join point.
* **Advice**: Once pointcuts are defined, we need to decide what to do with them and for that we use methods called *advices*. 
  An advice is an action taken by an aspect at a particular join point. There are different types of advices:
    * **<code>@Before</code>**: The advice will be called before the join point.
    * **<code>@After</code>**: The advice will be called after the join point, irrespective of whether it has thrown an exception or not.
    * **<code>@Around</code>**: This kind of advice can be invoked before and after the join point method is called.
    * **<code>@AfterReturning</code>**: The advice will be called after the join point, unless it will throw an exception.
    * **<code>@AfterThrowing</code>**: The advice will be called after the join point, but only then when it will throw an
      exception.
* **Aspect**: A place where several pointcuts are coupled with their advices is called an *aspect*. It is also the
  location of the new logic you want to add to the existing class, method, classes or methods. The aspect is the
  modularisation of a concern that cuts across multiple classes and/or methods.
* **AOP proxy**: An object created by the AOP framework in order to implement the aspect contracts (advice method
  executions and so on). In the Spring Framework, an AOP proxy will be a JDK dynamic proxy or a CGLIB proxy.
* **Weaving**: The Weaver is the framework that implements AOP — AspectJ or Spring AOP. Weaving links aspects with other
  application types or objects to create an advised object. The weaver scans the components in the `ApplicationContext`
  and dynamically generates code behind the scenes.


## Pointcut deep-dive

A pointcut is an expression language of Spring AOP that is used to match the target methods to apply the advice (i.e. a
predicate). It has two parts, one is the method signature consisting of the method name and parameters. The other one is the
pointcut expression which determines exactly which method we are applying the advice to. Spring’s pointcut model enables
pointcut reuse independent of advice types. This also allows us to target a different advice with the same pointcut.

### Pointcut Designators

A pointcut expression starts with a pointcut designator (PCD), a keyword telling Spring AOP what to match.
There are several pointcut designators, such as the execution of a method, a type, method arguments, or annotations.

<ol>
<li><code>execution</code>: The primary Spring PCD is execution, which matches method execution join points.

`@Pointcut("execution(public String com.example.pointcutadvice.dao.FooDao.findById(Long))")`

This PCD will match exactly the execution of `findById` method of the `FooDao` class. This works, but it is
not very flexible. Suppose we would like to match all the methods of the `FooDao` class, which may have different
signatures, return types, and arguments. To achieve this, we may use wildcards:

`@Pointcut("execution(* com.example.pointcutadvice.dao.FooDao.*(..))")`

Here the first wildcard matches any return value, the second matches any method name, and the `(..)` pattern matches any
number of parameters (zero or more).
</li>
<li><code>within</code>: Another way to achieve the same result from the previous section is by using the within PCD, 
which limits matching to join points of certain types.

`@Pointcut("within(com.example.pointcutadvice.dao.FooDao)")`

We could also match any type within the `com.example` package or a sub-package:

`@Pointcut("within(com.example..*)")`
</li>
<li><code>@within</code> This PCD limits matching to join points within types that have the given annotation:

`@Pointcut("@within(org.springframework.stereotype.Repository)")`

Which is equivalent to:
`@Pointcut("within(@org.springframework.stereotype.Repository *)")`
</li>
<li><code>this</code> and <code>target</code>: this limits matching to join points where the bean reference is an instance of the given type, 
while target limits matching to join points where the target object is an instance of the given type. 
The former works when Spring AOP creates a CGLIB-based proxy, and the latter is used when a JDK-based proxy is created. 

```
public class FooDao implements BarDao {
    ...
}
```

In this case, Spring AOP will use the JDK-based proxy and you should use the target PCD because the proxied object will
be an instance of Proxy class and implement the `BarDao` interface:

`@Pointcut("target(com.example.pointcutadvice.dao.BarDao)")`

On the other hand, if `FooDao` doesn't implement any interface or the `proxyTargetClass` property is set to true then
the proxied object will be a subclass of FooDao and the this PCD could be used:

`@Pointcut("this(com.example.pointcutadvice.dao.FooDao)")`
</li>
<li><code>@target</code>: The <code>@target</code> PCD limits matching to join points where the class of the executing object has an annotation of the given type:

`@Pointcut("@target(org.springframework.stereotype.Repository)")`
</li>
<li><code>args</code>: This PCD matches any method that starts with find and has only one parameter of type <code>Long</code>. If we want to match a method with any number of parameters but having the first parameter of type <code>Long</code>, we could use the following expression:

`@Pointcut("execution(* *..find*(Long,..))")`
</li>
<li><code>@args</code>: This PCD limits matching to join points where the runtime type of the actual arguments passed have annotations of the given type(s). Suppose that we want to trace all the methods accepting beans annotated with <code>@Entity</code> annotation:

`@Pointcut("@args(com.example.pointcutadvice.annotations.Entity)")`
`public void methodsAcceptingEntities() {}`

To access the argument we should provide a join point argument to the advice:

```@Before("methodsAcceptingEntities()")
public void logMethodAcceptionEntityAnnotatedBean(JoinPoint jp) {
    logger.info("Accepting beans with @Entity annotation: " + jp.getArgs()[0]);
}
```

</li>
<li><code>@annotation</code>: This PCD limits matching to join points where the subject of the join point has the given annotation. 
For example, we may create a <code>@Loggable</code> annotation:

```
@Pointcut("@annotation(com.example.pointcutadvice.annotations.Loggable)")
public void loggableMethods() {}
```

</li>
Then we may log execution of the methods marked by that annotation:

```
@Before("loggableMethods()")
public void logMethod(JoinPoint jp) {
    String methodName = jp.getSignature().getName();
    logger.info("Executing method: " + methodName);
}
```

</ol>

### Pointcut signatures

execution(<access_specifier> <package_name> <class_name> <method_name> (<arguments_list>))

* `execution(public * *(..))`: the execution of any public method.
* `execution(* calculate*(..))`: the execution of any method with a name beginning with `calculate`.
* `execution(* com.ex.SimpleCalculator.*(..))`: the execution of any method defined by the SimpleCalculator class.
* `execution(public  com.ex.SimpleCalculator.*(..))`: the execution of only public method defined by the
  SimpleCalculator class.
* `execution(* com.ex.*.*(..))`: the execution of any method defined in the com.ex package.
* `execution(* com.ex..*.*(..))`: the execution of any method defined in the com.ex package or a sub-package.
* `within(com.ex.*)`: any join point (method execution only in Spring AOP) within the com.ex package.
* `within(com.ex..*)`: any join point (method execution only in Spring AOP) within the com.ex package or a sub-package.
* `args(java.io.Serializable)`: any join point (method execution only in Spring AOP) which takes a single parameter, and
  where the argument passed at runtime is Serializable.
* `execution(* *(java.io.Serializable))`: note that the pointcut given in this example is different to the args version
  matches if the argument passed at runtime is Serializable, the execution version matches if the method signature
  declares a single parameter of type `Serializable`.
* `@annotation(org.springframework.transaction.annotation.Transactional)`: any join point (method execution only in
  Spring AOP) where the executing method has an `@Transactional` annotation.
* `bean(simpleCalc)`: any join point (method execution only in Spring AOP) on a Spring bean named `simpleCalc`.
* `bean(*service)`: any join point (method execution only in Spring AOP) on Spring beans having names that match the
  wildcard expression <code>*service</code>.

### Combining pointcut expressions

Pointcut expressions can be combined using `&&`, `||` and `!` operators:

```
@Pointcut("@target(org.springframework.stereotype.Repository)")
public void repositoryMethods() {}
 
@Pointcut("execution(* *..create*(Long,..))")
public void firstLongParamMethods() {}
 
@Pointcut("repositoryMethods() && firstLongParamMethods()")
public void entityCreationMethods() {}
```

## Resources

* https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-api
* https://medium.com/@wkrzywiec/moving-into-next-level-in-user-log-events-with-spring-aop-3b4435892f16
* https://www.baeldung.com/spring-aop-pointcut-tutorial

