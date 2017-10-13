package com.blackboard.testing.lambda;

import com.blackboard.testing.lambda.logger.LoggerContainer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @JsonIgnore
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LoggerContainer.LOGGER.log(e);
            return super.toString();
        }
    }
}
