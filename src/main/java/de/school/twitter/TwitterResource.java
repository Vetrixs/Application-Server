package de.school.twitter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterResource {

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello!";
    }
}
