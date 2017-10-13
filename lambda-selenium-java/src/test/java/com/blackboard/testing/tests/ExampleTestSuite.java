package com.blackboard.testing.tests;

import static com.blackboard.testing.lambda.logger.LoggerContainer.LOGGER;

import com.blackboard.testing.lambda.LambdaTestSuite;
import com.blackboard.testing.lambda.TestInvoker;
import com.blackboard.testing.lambda.TestRequest;
import com.blackboard.testing.lambda.TestResult;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.categories.Categories.CategoryFilter;
import org.junit.runners.Parameterized.Parameters;

public class ExampleTestSuite extends LambdaTestSuite {

    private static final CategoryFilter filter = CategoryFilter.include(Test.class);
    private TestRequest testRequest;

    public ExampleTestSuite(TestRequest testRequest) {
        this.testRequest = testRequest;
    }

    @Parameters(name = "{0}")
    public static Collection<TestRequest> testRequests() {
        LOGGER.log("Running " + filter.describe());

        return getTestRequests("com.blackboard.testing.tests", filter);
    }

    @Test
    public void runTest() {
        TestInvoker testInvoker = new TestInvoker(testRequest);
        TestResult testResult = testInvoker.run();
        writeAttachments(testResult.getAttachments());
        logTestResult(testRequest, testResult);
    }
}
