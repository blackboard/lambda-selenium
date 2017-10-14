# lambda-selenium [![Build Status](https://travis-ci.org/blackboard/lambda-selenium.svg?branch=master)](https://travis-ci.org/blackboard/lambda-selenium)

## lambda-selenium-java
#### Running The Example
* [Download the latest release  - java_lambda_function.zip](https://github.com/blackboard/lambda-selenium/releases/latest)
* Upload the release zip into an s3 bucket and copy the link for the file.
* Create a new Lambda Function in AWS with the following name

Note: for the tests to execute this name is required
```$xslt
lambda-selenium-function
```

* Change the settings of the Lambda Function
1. Set the Runtime to Java 8
2. Set the Handler to com.blackboard.testing.lambda.LambdaTestHandler::handleRequest
3. Set the Memory to 1536 MB
4. Set the Timeout to 5 min
5. Set the Code entry type to Upload a file from Amazon S3
6. Paste in the location of the package previously uploaded into the S3 link URL
7. Save the changes

Note: S3 is required because of the size of the package.

Now the function is ready to be invoked.

* Clone the repository and cd into the java example
```$xslt
git clone git@github.com:blackboard/lambda-selenium.git
cd lambda-selenium/lambda-selenium-java/
```

* Run the parameterized test suite ExampleTestSuite using an IDE or from the command line with:
```$xslt
gradle clean test
```

When the tests are ran, they will be executed in parallel by invoking the Lambda function that was created.
Once the tests are complete, open up the build/screenshots folder to view screenshots taken inside the running tests.
If a test fails, the exception will be thrown for the parameterized test case and logged to the console.


#### Packaging A New Function

To package the jar, run the following command inside the lambda-selenium-java directory
```$xslt
gradle clean unzipLibs shadowJar
```

This will package all of the required dependencies and code within the jar file.
The libraries that we included in the resources folder will also be included in this jar.
Once the jar has been built, you can find it in the build/libs folder.
This jar can then be deployed in a new lambda function.

Note: If you change the name to a different lambda function, you must change the name of the 
function that is invoked in the code in the class com.blackboard.testing.lambda.LambdaSeleniumService
