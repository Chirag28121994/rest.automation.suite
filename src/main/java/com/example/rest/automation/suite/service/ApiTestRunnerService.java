package com.example.rest.automation.suite.service;

import com.example.rest.automation.suite.model.ExecutionParams;
import com.example.rest.automation.suite.model.TestRunResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.rest.automation.suite.constants.Constants.*;

@Service
public class ApiTestRunnerService {
    Logger log  = LoggerFactory.getLogger(ApiTestRunnerService.class);
    public TestRunResult runTests(ExecutionParams executionParams) {
        log.info("runTests called execution Params: {}", executionParams);
        if (executionParams == null) {
            log.error("execution Params is null");
            return new TestRunResult("", -1, "execution Params map can't be null");
        }

        String gradleCmd = IS_WINDOWS ? "gradlew.bat" : "./gradlew";

        StringBuilder finalCommand = new StringBuilder(gradleCmd);
        finalCommand.append(" clean test ");

        String groups = executionParams.getGroups();
        if (groups != null && !groups.isEmpty()) {
            finalCommand.append("-Dgroups =").append(groups).append(" ");
        }

        //this should be the last 2nd last argument of the list
        String testName = executionParams.getTestName();
        if (testName != null && !testName.isEmpty()) {
            finalCommand.append("--tests").append(testName).append(" ");
        }

        Boolean debug = executionParams.getDebug();
        if (debug != null && debug) {
            finalCommand.append("--stacktrace --debug").append(testName).append(" ");
        }

        log.info("final executing command: {}", finalCommand);
        java.util.List<String> cmdList = new java.util.ArrayList<>();
        cmdList.add(gradleCmd);
        cmdList.add("clean");
        cmdList.add("test");
        cmdList.add("-Dgroups=" + groups);
        if (testName != null && !testName.isEmpty()) {
            cmdList.add("--tests");
            cmdList.add(testName);
        }
        if (debug) {
            cmdList.add("--stacktrace");
            cmdList.add("--debug");
        }
        log.info("ProcessBuilder command list: {}", cmdList);
        log.info("ProcessBuilder working directory: {}", USER_DIR);
        StringBuilder output = new StringBuilder();
        int exitCode = -1;
        try {
            ProcessBuilder pb = new ProcessBuilder(cmdList);
            pb.directory(new java.io.File(USER_DIR));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder logs = new StringBuilder();
            String summary = null;
            String duration = null;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                logs.append(line).append("\n");
                if (line.toLowerCase().contains("summary")) summary = line;
                if (line.toLowerCase().contains("duration")) duration = line;
            }
            exitCode = process.waitFor();
            log.info("Test process finished with exitCode: {}", exitCode);
            if (exitCode != 0) {
                log.error("Test process failed. Output: {}", output);
                return new TestRunResult(output.toString(), exitCode, "Test process failed", summary, duration, logs.toString(), null);
            }
            return new TestRunResult(output.toString(), exitCode, null, summary, duration, logs.toString(), REPORT_DIR);
        } catch (IOException e) {
            log.error("IOException during test run: {}", e.getMessage(), e);
            return new TestRunResult(output.toString(), exitCode, "IOException: " + e.getMessage(), null, null, null, null);
        } catch (InterruptedException e) {
            log.error("InterruptedException during test run: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            return new TestRunResult(output.toString(), exitCode, "InterruptedException: " + e.getMessage(), null, null, null, null);
        } catch (Exception e) {
            log.error("Unexpected exception during test run: {}", e.getMessage(), e);
            return new TestRunResult(output.toString(), exitCode, "Unexpected error: " + e.getMessage(), null, null, null, null);
        }
    }

}
