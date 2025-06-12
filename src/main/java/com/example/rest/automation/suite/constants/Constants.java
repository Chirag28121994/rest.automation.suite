package com.example.rest.automation.suite.constants;

public class Constants {
    public static final String OPERATING_SYSTEM = System.getProperty("os.name");
    public static final Boolean IS_WINDOWS = OPERATING_SYSTEM.toLowerCase().contains("win");
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String REPORT_DIR = USER_DIR + "/build/reports/tests/test/index.html";
}
