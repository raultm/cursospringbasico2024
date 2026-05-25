package com.example.curso2024.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
    
    @GetMapping("/")
    public String hello(){
        return "{ 'message': 'Hello World!'}";
    }
}
