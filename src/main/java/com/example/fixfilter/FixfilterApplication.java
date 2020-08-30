package com.example.fixfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class FixfilterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FixfilterApplication.class, args);
        System.out.println();
    }

}
