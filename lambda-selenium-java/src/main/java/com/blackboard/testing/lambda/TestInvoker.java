package com.blackboard.testing.lambda;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

public class TestInvoker {

    private final TestRequest request;

    public TestInvoker(TestRequest request) {
        this.request = request;
    }

    public TestResult run() {
        final LambdaSeleniumService lambdaService = LambdaInvokerFactory.builder()
                .lambdaClient(AWSLambdaClientBuilder.defaultClient())
                .build(LambdaSeleniumService.class);

        return lambdaService.runTest(request);
    }
}
