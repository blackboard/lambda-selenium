package com.blackboard.testing.lambda;

import static java.util.Optional.ofNullable;

import com.blackboard.testing.common.LambdaBaseTest;
import com.blackboard.testing.runner.ParallelParameterized;
import com.blackboard.testing.testcontext.TestUUID;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filter;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(ParallelParameterized.class)
public class LambdaTestSuite {

    private static final Logger logger = LoggerFactory.getLogger(LambdaTestSuite.class);

    private static List<Class> getTestClasses(String folderName) {
        Reflections reflections = new Reflections(folderName);
        Set<Class<? extends LambdaBaseTest>> allClasses = reflections.getSubTypesOf(LambdaBaseTest.class);
        List<Class> classes = new ArrayList<>();
        classes.addAll(allClasses);
        return classes;
    }

    protected static List<TestRequest> getTestRequests(String folderName, Filter filter) {
        List<TestRequest> requests = new ArrayList<>();
        getTestClasses(folderName).forEach(testClass -> {
            try {
                new BlockJUnit4ClassRunner(testClass).getDescription().getChildren()
                        .forEach(description -> {
                            if (filter.shouldRun(description)) {
                                TestRequest request = new TestRequest(description);
                                request.setTestRunUUID(TestUUID.getTestUUID());
                                requests.add(request);
                            }
                        });
            } catch (InitializationError e) {
                logger.error("Test Request Initialization Error", e);
            }
        });
        return requests;
    }

    protected void writeAttachments(Map<String, byte[]> attachments) {
        File outputDirectory = new File(System.getProperty("user.dir") + "/build/screenshots/");
        outputDirectory.mkdirs();

        attachments.forEach((fileName, bytes) -> {
            try {
                FileUtils.writeByteArrayToFile(new File(outputDirectory, fileName), bytes);
            } catch (IOException e) {
                logger.error("Unable to write screenshots to file", e);
            }
        });
    }

    protected void logTestResult(TestRequest request, TestResult result) {
        logger.info("Test %s:%s completed.", request.getTestClass(), request.getFrameworkMethod());
        if (ofNullable(result.getThrowable()).isPresent()) {
            throw new RuntimeException(result.getThrowable());
        }
    }
}
