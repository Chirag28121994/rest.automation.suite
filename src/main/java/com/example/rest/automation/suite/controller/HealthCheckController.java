package com.example.rest.automation.suite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HealthCheckController {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Health check controller being called.");
        return "Runner App is working perfect.. !!";
    }

}
