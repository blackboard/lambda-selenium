package com.blackboard.testing.testcontext;

import static java.util.Optional.ofNullable;

public class EnvironmentDetector {

    public static boolean inLambda() {
        return ofNullable(System.getenv("LAMBDA_RUNTIME_DIR")).isPresent();
    }
}
