package com.blackboard.testing.driver;

import com.codeborne.selenide.webdriver.WebDriverFactory;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LambdaWebDriverFactory extends WebDriverFactory {

    private DesiredCapabilities desiredCapabilities;

    public LambdaWebDriverFactory() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, getLambdaChromeOptions());
        capabilities.setBrowserName(this.getClass().getCanonicalName());
        desiredCapabilities = capabilities;
    }

    private ChromeOptions getLambdaChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/var/task/chrome");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        options.addArguments("--window-size=1366,768");
        options.addArguments("--single-process");
        options.addArguments("--no-sandbox");
        options.addArguments("--user-data-dir=/tmp/user-data");
        options.addArguments("--data-path=/tmp/data-path");
        options.addArguments("--homedir=/tmp");
        options.addArguments("--disk-cache-dir=/tmp/cache-dir");
        return options;
    }

    @Override
    public WebDriver createWebDriver(Proxy proxy) {
        return new ChromeDriver(desiredCapabilities);
    }
}
