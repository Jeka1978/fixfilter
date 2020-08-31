package com.example.fixfilter;/**
 * @author Evgeny Borisov
 */

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@RestController
public @interface FrontendController {
}
