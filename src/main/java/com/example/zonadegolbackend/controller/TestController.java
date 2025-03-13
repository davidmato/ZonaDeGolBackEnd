package com.example.zonadegolbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("message", "El backend está conectado correctamente con Angular");
    }
}

