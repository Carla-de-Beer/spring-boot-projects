package com.cadebe.github_reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GitHubReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubReaderApplication.class, args);
    }

}
