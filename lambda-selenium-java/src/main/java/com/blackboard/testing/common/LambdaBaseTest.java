package com.blackboard.testing.common;

import com.blackboard.testing.driver.LambdaWebDriverThreadLocalContainer;
import com.blackboard.testing.testcontext.EnvironmentDetector;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName;
import org.junit.BeforeClass;

public abstract class LambdaBaseTest {

    @BeforeClass
    public static void baseTestBeforeClass() {
        Configuration.browser = BrowserName.CHROME.toString();
        Configuration.reopenBrowserOnFail = false;

        if (EnvironmentDetector.inLambda()) {
            Configuration.reportsFolder = "/tmp/selenidereports";
            WebDriverRunner.webdriverContainer = new LambdaWebDriverThreadLocalContainer();
        }
    }
}
