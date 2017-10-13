package com.blackboard.testing.runner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.runners.model.RunnerScheduler;

public class ThreadPoolScheduler implements RunnerScheduler {

    private int timeoutMinutes;
    private ExecutorService executor;

    public ThreadPoolScheduler(int maxThreads, int timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
        executor = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void schedule(Runnable childStatement) {
        executor.submit(childStatement);
    }

    @Override
    public void finished() {
        executor.shutdown();
        try {
            executor.awaitTermination(timeoutMinutes, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
