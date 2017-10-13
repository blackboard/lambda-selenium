package com.blackboard.testing.lambda;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

public class TestInvoker {

    private final TestRequest request;

    public TestInvoker(TestRequest request) {
        this.request = request;
    }

    public InvokeResult run(String lambdaFunctionName) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(lambdaFunctionName)
                .withInvocationType(InvocationType.RequestResponse)
                .withPayload(request.toString());

        return AWSLambdaClientBuilder.defaultClient().invoke(invokeRequest);
    }
}
