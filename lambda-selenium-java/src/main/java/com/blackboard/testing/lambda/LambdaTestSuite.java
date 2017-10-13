package com.blackboard.testing.lambda;

import static com.blackboard.testing.lambda.logger.LoggerContainer.LOGGER;

import com.blackboard.testing.common.LambdaBaseTest;
import com.blackboard.testing.runner.ParallelParameterized;
import com.blackboard.testing.testcontext.TestUUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filter;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.reflections.Reflections;

@RunWith(ParallelParameterized.class)
public class LambdaTestSuite {

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
                LOGGER.log(e);
            }
        });
        return requests;
    }
}
