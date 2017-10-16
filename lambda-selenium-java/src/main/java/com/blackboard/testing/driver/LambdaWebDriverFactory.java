package com.blackboard.testing.driver;

import com.codeborne.selenide.webdriver.WebDriverFactory;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LambdaWebDriverFactory extends WebDriverFactory {

    public LambdaWebDriverFactory() {
        System.setProperty("webdriver.chrome.driver", getLibLocation("chromedriver"));
    }

    private ChromeOptions getLambdaChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(getLibLocation("chrome"));
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

    private String getLibLocation(String lib) {
        return String.format("%s/lib/%s", System.getenv("LAMBDA_TASK_ROOT"), lib);
    }

    @Override
    public WebDriver createWebDriver(Proxy proxy) {
        return new ChromeDriver(getLambdaChromeOptions());
    }
}
