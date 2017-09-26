package com.oocl.jenkinsdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Xuesong Mei on 23/09/2017.
 */
@RestController
public class Sample {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/hello2")
    public String hello(@RequestParam String name) {
        return "Hello, " + name;
    }
}
