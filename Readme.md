# READ ME

## Goal 
Run API tests in whole on different environments with as less time needed for execution and test data preparation and provide test run report.

## Tech stack:
Java
TestNG
RestAssured
Maven

## Basis:
Test cases are written in Test Rail and can be found at https://testrail.ebs.crealogix.net/index.php?/suites/view/24543.
These test cases are then automated in Java using TestNG as basis for test execution and RestAssured as the API testing framework.

## Groups:
Tests are grouped in two types of categories:
- By module:
  - dynamicform
  - formroute
  - fundings
  - partner
  - tasks
  - usermanagement
- By TP:
  - TP1
  - TP4

This way, a group of tests from a specific module or TP can be executed using the testng.xml file to filter appropriate tests. With further development and introduction of new modules or TPs the list can easily be expanded.

## Environment files:
In order to execute tests on different environment under the src/main/resources/org/apitests/ path the config.properties file is found. This file has two parameters:
- environment parameter:
Specifies the name of the environment on which the tests will run with. The name of the environment is also the name of the property file in the same path that contains different variables for the environment and test data needed.
- createTestRailRun parameter:
Boolean value that defines if a TestRail run should be created. If true, test run will be created and can be used for reporting. If false, TestRail run will not be created.

Ongoing design and development are being taken so that the test data being created for a test run will be as minimal as possible and thus require minimal effort for setting up when running on new environments.