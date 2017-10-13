package com.blackboard.testing.lambda;

import static java.util.Optional.ofNullable;

import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

public class TestRequest {

    private String testClass;
    private String frameworkMethod;
    private String testRunUUID;

    public TestRequest() {}

    public TestRequest(Class<?> clazz, FrameworkMethod method) {
        testClass = clazz.getCanonicalName();
        frameworkMethod = method.getMethod().getName();
    }

    public TestRequest(Description description) {
        testClass = description.getClassName();
        frameworkMethod = description.getMethodName();
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getFrameworkMethod() {
        return frameworkMethod;
    }

    public void setFrameworkMethod(String method) {
        this.frameworkMethod = method;
    }

    public String getTestRunUUID() {
        return testRunUUID;
    }

    public void setTestRunUUID(String testRunUUID) {
        this.testRunUUID = testRunUUID;
    }

    @Override
    public String toString() {
        return ofNullable(testClass).orElse("null") + ":" + ofNullable(frameworkMethod).orElse("null");
    }
}
