package com.cadebe.github_reader.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(value = Exception.class)
    public String handleError(Exception exception, Model model) {
        log.error("An exception was caught in accessing the GitHubReader. Exception info: '{}'.", exception.getMessage());
        model.addAttribute("errorMsg", exception.getMessage());
        return "error";
    }
}
