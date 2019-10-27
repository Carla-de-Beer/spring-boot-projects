package com.cadebe.client_application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldRestController {

    @GetMapping("/hello-world/{name}")
    public String getHelloWorld(@PathVariable String name) {
        return "Hello World " + name;
    }

}