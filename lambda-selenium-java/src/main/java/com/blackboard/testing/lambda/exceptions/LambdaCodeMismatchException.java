package com.blackboard.testing.lambda.exceptions;

public class LambdaCodeMismatchException extends RuntimeException {

    public LambdaCodeMismatchException(String className) {
        super(String.format("Test class %s was not found, the test code is likely newer than the code uploaded to lambda. Redeploy and try again", className));
    }
}
