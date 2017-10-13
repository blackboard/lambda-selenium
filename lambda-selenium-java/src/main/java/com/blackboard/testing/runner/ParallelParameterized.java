package com.blackboard.testing.runner;

import static java.lang.Integer.parseInt;

import org.junit.runners.Parameterized;

public class ParallelParameterized extends Parameterized {

    public ParallelParameterized(Class klass) throws Throwable {
        super(klass);

        String maxThreads = System.getProperty("testing.parallel.threads", "256");
        String parallelTimeout = System.getProperty("testing.parallel.timeout", "10");

        setScheduler(new ThreadPoolScheduler(parseInt(maxThreads), parseInt(parallelTimeout)));
    }
}
