package com.example.fixfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbayProductController {

    @PostMapping("/product")
    public void printProduct(@RequestBody Product product) {
        System.out.println(product+" was saved");
    }

    @PostMapping("/person")
    @ForFrontend
    public Person printPerson(@RequestBody Person person) {
        System.out.println("person = " + person);
        return person;
    }
}
