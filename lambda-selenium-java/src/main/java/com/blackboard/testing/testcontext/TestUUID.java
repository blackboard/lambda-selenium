package com.blackboard.testing.testcontext;

import java.util.UUID;

public class TestUUID {

    private TestUUID() {}

    private static final String DEFAULT_TEST_UUID = UUID.randomUUID().toString();

    public static String getTestUUID() {
        return System.getProperty("target.test.uuid", DEFAULT_TEST_UUID);
    }
}
