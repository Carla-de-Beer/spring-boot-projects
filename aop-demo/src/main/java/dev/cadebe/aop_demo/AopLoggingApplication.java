package dev.cadebe.aop_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AopLoggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopLoggingApplication.class, args);
    }
}
