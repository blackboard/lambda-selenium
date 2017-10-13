package com.blackboard.testing.lambda;

import static com.blackboard.testing.lambda.logger.LoggerContainer.LOGGER;
import static java.util.Optional.ofNullable;

import com.blackboard.testing.lambda.exceptions.LambdaCodeMismatchException;
import com.blackboard.testing.lambda.logger.Logger;
import com.blackboard.testing.lambda.logger.LoggerContainer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Optional;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runners.BlockJUnit4ClassRunner;

public class LambdaTestHandler implements RequestHandler<TestRequest, LambdaTestResult> {

    private static LambdaTestResult lambdaTestResult;

    public LambdaTestHandler() {
        lambdaTestResult = new LambdaTestResult();
    }

    public LambdaTestResult handleRequest(TestRequest testRequest, Context context) {
        LoggerContainer.LOGGER = new Logger(context.getLogger());
        System.setProperty("target.test.uuid", testRequest.getTestRunUUID());

        Optional<Result> result = Optional.empty();
        try {
            BlockJUnit4ClassRunner runner = new BlockJUnit4ClassRunner(getTestClass(testRequest));
            runner.filter(new MethodFilter(testRequest.getFrameworkMethod()));

            result = ofNullable(new JUnitCore().run(runner));
        } catch (Exception e) {
            lambdaTestResult.setThrowable(e);
            LOGGER.log(e);
        }

        if (result.isPresent()) {
            lambdaTestResult.setRunCount(result.get().getRunCount());
            lambdaTestResult.setRunTime(result.get().getRunTime());
            LOGGER.log("Run count: " + result.get().getRunCount());
            result.get().getFailures().forEach(failure -> {
                LOGGER.log(failure.getException());
                lambdaTestResult.setThrowable(failure.getException());
            });
        }

        return lambdaTestResult;
    }

    private Class getTestClass(TestRequest testRequest) {
        LOGGER.log("Running Test: %s::%s", testRequest.getTestClass(), testRequest.getFrameworkMethod());
        try {
            LOGGER.log(testRequest.getTestClass());
            LOGGER.log(testRequest.getFrameworkMethod());
            return Class.forName(testRequest.getTestClass());
        } catch (ClassNotFoundException e) {
            LOGGER.log(e);
            throw new LambdaCodeMismatchException(testRequest.getTestClass());
        }
    }

    public static void addAttachment(String fileName, byte[] attachment) {
        lambdaTestResult.getAttachments().put(fileName, attachment);
    }
}
