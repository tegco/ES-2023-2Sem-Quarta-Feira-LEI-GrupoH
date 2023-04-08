package pt.iscteiul.gestaohorarios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello World from Backend!";
    }
}