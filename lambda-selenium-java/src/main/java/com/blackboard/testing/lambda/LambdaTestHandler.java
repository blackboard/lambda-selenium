package com.blackboard.testing.lambda;

import static java.util.Optional.ofNullable;

import com.blackboard.testing.lambda.exceptions.LambdaCodeMismatchException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Optional;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaTestHandler implements RequestHandler<TestRequest, TestResult> {

    private static final Logger logger = LoggerFactory.getLogger(LambdaTestHandler.class);

    private static TestResult testResult;

    public LambdaTestHandler() {
        testResult = new TestResult();
    }

    public TestResult handleRequest(TestRequest testRequest, Context context) {
        System.setProperty("target.test.uuid", testRequest.getTestRunUUID());

        Optional<Result> result = Optional.empty();
        try {
            BlockJUnit4ClassRunner runner = new BlockJUnit4ClassRunner(getTestClass(testRequest));
            runner.filter(new MethodFilter(testRequest.getFrameworkMethod()));

            result = ofNullable(new JUnitCore().run(runner));
        } catch (Exception e) {
            testResult.setThrowable(e);
            logger.error("Test Error", e);
        }

        if (result.isPresent()) {
            testResult.setRunCount(result.get().getRunCount());
            testResult.setRunTime(result.get().getRunTime());
            logger.info("Run count: %s", result.get().getRunCount());
            result.get().getFailures().forEach(failure -> {
                logger.error(failure.getMessage(), failure.getException());
                testResult.setThrowable(failure.getException());
            });
        }

        return testResult;
    }

    private Class getTestClass(TestRequest testRequest) {
        logger.info("Running Test: %s::%s", testRequest.getTestClass(), testRequest.getFrameworkMethod());
        try {
            logger.info(testRequest.getTestClass());
            logger.info(testRequest.getFrameworkMethod());
            return Class.forName(testRequest.getTestClass());
        } catch (ClassNotFoundException e) {
            logger.error("Unable to find class", e);
            throw new LambdaCodeMismatchException(testRequest.getTestClass());
        }
    }

    public static void addAttachment(String fileName, byte[] attachment) {
        testResult.getAttachments().put(fileName, attachment);
    }
}
