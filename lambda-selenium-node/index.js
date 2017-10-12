'use strict';

exports.handler = (event, context, callback) => {
    var webdriver = require('selenium-webdriver');
    var chrome = require('selenium-webdriver/chrome');
    var builder = new webdriver.Builder().forBrowser('chrome');
    var chromeOptions = new chrome.Options();
    const defaultChromeFlags = [
        '--headless',
        '--disable-gpu',
        '--window-size=1280x1696', // Letter size
        '--no-sandbox',
        '--user-data-dir=/tmp/user-data',
        '--hide-scrollbars',
        '--enable-logging',
        '--log-level=0',
        '--v=99',
        '--single-process',
        '--data-path=/tmp/data-path',
        '--ignore-certificate-errors',
        '--homedir=/tmp',
        '--disk-cache-dir=/tmp/cache-dir'
    ];

    chromeOptions.setChromeBinaryPath("/var/task/lib/chrome");
    chromeOptions.addArguments(defaultChromeFlags);
    builder.setChromeOptions(chromeOptions);

    var driver = builder.build();
    driver.get(event.url);
    driver.getTitle().then(function(title) {

        console.log("Page title for " + event.url + " is " + title)
        callback(null, 'Page title for ' + event.url + ' is ' + title);
    });

    driver.quit();
};


