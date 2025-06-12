package com.example.rest.automation.suite.model;


public class ExecutionParams {
    private String testName;
    private String groups;
    private Boolean debug;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "ExecutionParams{" +
                "testName='" + testName + '\'' +
                ", groups='" + groups + '\'' +
                ", debug=" + debug +
                '}';
    }
}
