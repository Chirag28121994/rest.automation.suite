package com.example.rest.automation.suite.controller;

import com.example.rest.automation.suite.model.ExecutionParams;
import com.example.rest.automation.suite.model.TestRunResult;
import com.example.rest.automation.suite.service.ApiTestRunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ApiTestRunnerController {
    private final ApiTestRunnerService apiTestRunnerService;

    @Autowired
    public ApiTestRunnerController() {
        this.apiTestRunnerService = new ApiTestRunnerService();
    }

    @PostMapping("/run-tests")
    public ResponseEntity<String> runTests(@RequestBody(required = false) ExecutionParams executionParams) {
        TestRunResult runResult = apiTestRunnerService.runTests(executionParams);
        if (runResult.getError() != null) {
            return ResponseEntity.badRequest().body("Tests failed to run: \n" + runResult.getError() + "\n" + runResult.getOutput());
        }
        if (runResult.getExitCode().equals(0)) {
            return ResponseEntity.status(201).body("Tests Executed Successfully. \n " + runResult.getOutput());
        } else {
            return ResponseEntity.status(500).body("Error running tests: \n " + runResult.getOutput());
        }
    }
}
