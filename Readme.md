# READ ME

## Goal 
Run API tests in whole on different environments with as less time needed for execution and test data preparation and provide test run report.

## Tech stack:
- Java as coding language
- TestNG as test runner and assert management
- RestAssured as API testing framework
- Maven as project dependency management tool

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
In order to execute tests locally on different environment under the src/main/resources/org/apitests/ path the config.properties file is found. This file has two parameters:

1. environment parameter:
Specifies the name of the environment on which the tests will run with. The name of the environment is also the name of the property file in the same path that contains different variables for the environment and test data needed.
2. createTestRailRun parameter:
Boolean value that defines if a TestRail run should be created. If true, test run will be created and can be used for reporting. If false, TestRail run will not be created.

For execution via Jenkins the specific environment files are relevant as the jenkins parameter environment leads to these files.

Ongoing design and development are being taken so that the test data being created for a test run will be as minimal as possible and thus require minimal effort for setting up when running on new environments.

## Test data:
Following properties need to be setup for each new environment on which the tests are to be executed:

| Parameter             | Explanation                                                                               |
|-----------------------|-------------------------------------------------------------------------------------------|
| protocol              | https (always!)                                                                           |
| host                  | URL of the environment (e.g. fbk-r4-qa.k8s.crealogix.net)                                 |
| tenant	            | Tenant of the environment (e.g. qa4)                                                      |
| sid	                | Database identifier (e.g. FBK_R4_QA)                                                      |
| loginName             | Login name of the company user that is created manually                                   |
| partnerId	            | This can always be 20066 since it is loaded initially once the environment is setup       |
| fundingId	            | Funding ID that is created with the company user (should be HIF) manually (e.g. 82600013) |
| fundingExternalId     | The ID of the used HIF above retrieved from the CMS                                       |
| fundingMonitoringId   | The ID of the monitoring funding that is manually created meant for TP4 tests             |
| assigneeId	        | The UUID of the supervisor user of the created company                                    |
| taskId	            | This field is being removed from setup, soon it will be deprecated                        |
| taskFormRouteId	    | This field is being removed from setup, soon it will be deprecated                        |
| userUUID	            | UUID of the company user created                                                          |
| formrouteId	        | ID of the form route belonging to fundingId (e.g. 82600013-1)                             |