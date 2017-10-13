package com.blackboard.testing.lambda.logger;

import static java.util.Optional.ofNullable;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.glassfish.jersey.internal.util.ExceptionUtils;

public class Logger {

    private final LambdaLogger lambdaLogger;
    private boolean firstMessageLogged = false;

    public Logger(LambdaLogger lambdaLogger) {
        this.lambdaLogger = lambdaLogger;
    }

    private void logMessage(String message) {
        String newLine = "";
        if (!firstMessageLogged) {
            newLine += "\n";
        } else {
            firstMessageLogged = true;
        }
        lambdaLogger.log(newLine + ofNullable(message).orElse("null"));
    }

    public void log(Object message) {
        logMessage(String.format("%s: %s", message.getClass().getSimpleName(), ofNullable(message.toString()).orElse("null")));
    }

    public void log(String message) {
        logMessage(message);
    }

    public void log(Throwable t) {
        logMessage(ExceptionUtils.exceptionStackTraceAsString(t));
    }

    public void log(String format, Object... args) {
        logMessage(String.format(format, args));
    }
}
