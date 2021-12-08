package org.apitests.core;

import org.apitests.core.testrail.APIClient;
import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestRailRunner extends TestListenerAdapter {

    boolean CREATE_TEST_RUN = Boolean.parseBoolean(ResourceBundle.getBundle("org.apitests.config").getString("createTestRailRun"));
    String ENVIRONMENT = ResourceBundle.getBundle("org.apitests.config").getString("environment").toUpperCase(Locale.ROOT);
    long TEST_RUN_ID = 0;
    APIClient client = new APIClient("https://testrail.ebs.crealogix.net");
    String username = "eb-selenium@crealogix.com";
    String password = "SelRes11";
    int TEST_CASE_PASSED_STATUS = 1;
    int TEST_CASE_FAILED_STATUS = 5;
    int TEST_CASE_SKIPPED_STATUS = 4;
    String projectID = "14";
    String suiteID = "24543";
    boolean JENKINS_CREATE_TEST_RUN = Boolean.parseBoolean(System.getenv("createTestRailRun"));
    int JENKINS_USE_EXISTING_SUITE = 0;


    @Override
    public void onFinish(ITestContext context) {
        // Use an existing TestRail test run
        JENKINS_USE_EXISTING_SUITE = Integer.parseInt(System.getenv("useExistingSuite"));
        if(JENKINS_USE_EXISTING_SUITE > 0 && !CREATE_TEST_RUN && !JENKINS_CREATE_TEST_RUN){
            TEST_RUN_ID = JENKINS_USE_EXISTING_SUITE;
            client.setUser(username);
            client.setPassword(password);
            //Update test run with results
            //Iterator for passed tests
            Iterator<ITestResult> itrPassed = context.getPassedTests().getAllResults().iterator();
            while(itrPassed.hasNext()) {
                ITestResult itr = itrPassed.next();
                HashMap<Object, Object> dataTestResults = new HashMap<>();
                dataTestResults.put("status_id", TEST_CASE_PASSED_STATUS);
                dataTestResults.put("comment", "Test passed");
                try {
                    client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + Integer.valueOf(itr.getName().substring(5, 14)) + "", dataTestResults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reporter.log("Test case C" + itr.getName().substring(5, 14) + " passed.", true);
            }
            //Iterator for skipped tests
            Iterator<ITestResult> itrSkipped = context.getSkippedTests().getAllResults().iterator();
            while(itrSkipped.hasNext()) {
                ITestResult itr = itrSkipped.next();
                HashMap<Object, Object> dataTestResults = new HashMap<>();
                dataTestResults.put("status_id", TEST_CASE_SKIPPED_STATUS);
                dataTestResults.put("comment", "Skip reason: " + itr.getThrowable());
                try {
                    client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + Integer.valueOf(itr.getName().substring(5, 14)) + "", dataTestResults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reporter.log("Test case C" + itr.getName().substring(5, 14) + " skipped.", true);
            }
            //Iterator for failed tests
            Iterator<ITestResult> itrFailed = context.getFailedTests().getAllResults().iterator();
            while(itrFailed.hasNext()) {
                ITestResult itr = itrFailed.next();
                HashMap<Object, Object> dataTestResults = new HashMap<>();
                dataTestResults.put("status_id", TEST_CASE_FAILED_STATUS);
                dataTestResults.put("comment", "Fail reason: " + itr.getThrowable());
                try {
                    client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + Integer.valueOf(itr.getName().substring(5, 14)) + "", dataTestResults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reporter.log("Test case C" + itr.getName().substring(5, 14) + " failed.", true);
            }
        }

        // Create a new run for TestRail and populate results accordingly
        if (CREATE_TEST_RUN || JENKINS_CREATE_TEST_RUN) {
            //Create a test run
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
            Date date = new Date();
            client.setUser(username);
            client.setPassword(password);
            HashMap<Object, Object> dataTestRun = new HashMap<>();
            dataTestRun.put("include_all", false);
            List<Integer> arrCaseIds = new ArrayList<>();
            //Iterator for passed tests
            Iterator<ITestResult> itrPassed = context.getPassedTests().getAllResults().iterator();
            while(itrPassed.hasNext()){
                ITestResult itr = itrPassed.next();
                arrCaseIds.add(Integer.valueOf(itr.getName().substring(5, 14)));
            }
            //Iterator for failed tests
            Iterator<ITestResult> itrFailed = context.getFailedTests().getAllResults().iterator();
            while(itrFailed.hasNext()){
                ITestResult itr = itrFailed.next();
                arrCaseIds.add(Integer.valueOf(itr.getName().substring(5, 14)));
            }
            //Iterator for skipped tests
            Iterator<ITestResult> itrSkipped = context.getSkippedTests().getAllResults().iterator();
            while(itrSkipped.hasNext()){
                ITestResult itr = itrSkipped.next();
                arrCaseIds.add(Integer.valueOf(itr.getName().substring(5, 14)));
            }
            if(System.getenv("modulesToRun")==null || System.getenv("modulesToRun").isEmpty()) {
                dataTestRun.put("name", ENVIRONMENT + " API TestRun - " + formatter.format(date));
            }else{
                dataTestRun.put("name", ENVIRONMENT + " API "+System.getenv("modulesToRun").toUpperCase(Locale.ROOT)+" TestRun - " + formatter.format(date));
            }
            dataTestRun.put("case_ids", arrCaseIds);
            dataTestRun.put("description", "Description: Test run of automated API test for FBK");
            dataTestRun.put("suite_id", suiteID);
            dataTestRun.put("assignedto_id", 68);
            try {
                JSONObject c = (JSONObject) client.sendPost("add_run/" + projectID, dataTestRun);
                TEST_RUN_ID = (Long) c.get("id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Update test run with results
            //Iterator for passed tests
            itrPassed = context.getPassedTests().getAllResults().iterator();
            while(itrPassed.hasNext()) {
                ITestResult itr = itrPassed.next();
                HashMap<Object, Object> dataTestResults = new HashMap<>();
                dataTestResults.put("status_id", TEST_CASE_PASSED_STATUS);
                dataTestResults.put("comment", "Test passed");
                try {
                    client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + Integer.valueOf(itr.getName().substring(5, 14)) + "", dataTestResults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reporter.log("Test case C" + itr.getName().substring(5, 14) + " passed.", true);
            }
            //Iterator for skipped tests
            itrSkipped = context.getSkippedTests().getAllResults().iterator();
            while(itrSkipped.hasNext()) {
                ITestResult itr = itrSkipped.next();
                HashMap<Object, Object> dataTestResults = new HashMap<>();
                dataTestResults.put("status_id", TEST_CASE_SKIPPED_STATUS);
                dataTestResults.put("comment", "Skip reason: " + itr.getThrowable());
                try {
                    client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + Integer.valueOf(itr.getName().substring(5, 14)) + "", dataTestResults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reporter.log("Test case C" + itr.getName().substring(5, 14) + " skipped.", true);
            }
            //Iterator for failed tests
            itrFailed = context.getFailedTests().getAllResults().iterator();
            while(itrFailed.hasNext()) {
                ITestResult itr = itrFailed.next();
                HashMap<Object, Object> dataTestResults = new HashMap<>();
                dataTestResults.put("status_id", TEST_CASE_FAILED_STATUS);
                dataTestResults.put("comment", "Fail reason: " + itr.getThrowable());
                try {
                    client.sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + Integer.valueOf(itr.getName().substring(5, 14)) + "", dataTestResults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reporter.log("Test case C" + itr.getName().substring(5, 14) + " failed.", true);
            }
            Reporter.log("Test run is created.", true);
        }

    }

}
