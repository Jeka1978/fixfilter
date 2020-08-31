package com.example.fixfilter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FrontendController
public class EbayProductController {

    @PostMapping("/product")
    public void printProduct(@RequestBody Product product) {
        System.out.println(product+" was saved");
    }

    @PostMapping("/person")
    public Person printPerson(@RequestBody Person person) {
        System.out.println("person = " + person);
        return person;
    }
}
