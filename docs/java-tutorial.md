# Lambda-Selenium: Java Tutorial [![Build Status](https://travis-ci.org/blackboard/lambda-selenium.svg?branch=master)](https://travis-ci.org/blackboard/lambda-selenium)

#### Running The Example

**_Install the serverless framework_**
```shell
npm install serverless -g
```

**_Use shell commands to clone the repository and change the working directory to the Java example_**
```shell
git clone git@github.com:blackboard/lambda-selenium.git
cd lambda-selenium/lambda-selenium-java/
```

#### Packaging A New Function

**_To package the jar and deploy it, run the following command inside the lambda-selenium-java directory_**
```shell
gradle clean unzipLibs shadowJar deploy
```

This will package all of the required dependencies and code within the jar file.
The libraries that we included in the resources folder will also be included in this jar.
Once the jar has been built, you can find it in the build/libs folder.
This jar will then be deployed as a new lambda function using the [serverless framework](https://serverless.com/).

Note: If you change the name to a different lambda function in the [serverless.yml](../lambda-selenium-java/serverless.yml), you must change the name of the
function that is invoked in the code in the class com.blackboard.testing.lambda.LambdaSeleniumService

Now the function is ready to be invoked.

**_Next, run the test suite 'ExampleTestSuite' inside the same shell :_**
```shell
gradle clean test
```

When the tests are ran, they will be executed in parallel by invoking the Lambda function that was created.
Once the tests are complete, open folder './build/screenshots' to view screenshots taken inside the running tests.
If a test fails, the exception will be thrown for the test case and logged to the console.
