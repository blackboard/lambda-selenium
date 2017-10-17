package com.blackboard.testing.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

public interface LambdaSeleniumService {
    @LambdaFunction(functionName = "lambda-test-function-WesLambdaHandler-NH9NMZGQ546F")
    TestResult runTest(TestRequest testRequest);
}
