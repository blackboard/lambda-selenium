package com.blackboard.testing.lambda;

import static java.util.Optional.ofNullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.Result;

public class TestResult {

    private Throwable throwable;
    private int runCount;
    private int failureCount;
    private int ignoreCount;
    private long runTime;
    private Map<String, byte[]> attachments = new HashMap<>();

    public TestResult() {}

    public TestResult(Result result) {
        runCount = result.getRunCount();
        failureCount = result.getFailureCount();
        ignoreCount = result.getIgnoreCount();
        runTime = result.getRunTime();
        if (!wasSuccessful()) {
            throwable = result.getFailures().get(0).getException();
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public int getRunCount() {
        return runCount;
    }

    public void setRunCount(int runCount) {
        this.runCount = runCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(int ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public Map<String, byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, byte[]> attachments) {
        this.attachments = attachments;
    }

    @JsonIgnore
    public boolean wasSuccessful() {
        return !ofNullable(throwable).isPresent();
    }
}
