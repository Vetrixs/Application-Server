package de.school.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationResource {

    @GetMapping("/test")
    public String test(){
        return "HAHA";
    }
}
