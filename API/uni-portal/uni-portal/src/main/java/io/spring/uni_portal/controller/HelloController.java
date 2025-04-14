package io.spring.uni_portal.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from UniPortal!";
    }

    @PostMapping("/hello")
    public String postHello() {
        return "Hello from UniPortal!";
    }
}
