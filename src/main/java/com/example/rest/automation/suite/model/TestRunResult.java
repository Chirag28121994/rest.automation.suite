package com.example.rest.automation.suite.model;

public class TestRunResult {
    private  String output;
    private  Integer exitCode;
    private  String error;
    private  String summary;

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    private String reportUrl;

    public String getHtmlReport() {
        return htmlReport;
    }

    public void setHtmlReport(String htmlReport) {
        this.htmlReport = htmlReport;
    }

    private String htmlReport;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    private  String duration;
    private  String logs;

    public TestRunResult(String output, Integer exitCode, String error) {
       this(output, exitCode, error, null, null, null, null);
    }

    public TestRunResult(String output, Integer exitCode, String error, String summary, String duration, String logs, String reportUrl) {
        this.output = output;
        this.exitCode = exitCode;
        this.error = error;
        this.summary = summary;
        this.duration = duration;
        this.logs = logs;
        this.reportUrl = reportUrl;
    }
}
