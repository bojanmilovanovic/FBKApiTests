package org.apitests.core;

import org.apitests.core.testrail.APIClient;
import org.apitests.core.testrail.APIException;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestRailRunner extends TestListenerAdapter {

    boolean CREATE_TEST_RUN = Boolean.parseBoolean(ResourceBundle.getBundle("org.apitests.config").getString("createTestRailRun"));
    String ENVIRONMENT = ResourceBundle.getBundle("org.apitests.config").getString("environment").toUpperCase(Locale.ROOT);
    boolean TEST_RUN_IS_CREATED = false;
    long TEST_RUN_ID = 0;
    APIClient client = new APIClient("https://testrail.ebs.crealogix.net");
    String username = "eb-selenium@crealogix.com";
    String password = "SelRes11";
    int TEST_CASE_PASSED_STATUS = 1;
    int TEST_CASE_FAILED_STATUS = 5;
    int TEST_CASE_SKIPPED_STATUS = 4;
    String projectID = "14";
    String suiteID = "24543";
    boolean JENKINS_CREATE_TEST_RUN = Boolean.valueOf(System.getenv("createTestRailRun"));

    public void createTestRun(){
        if ((CREATE_TEST_RUN == true && TEST_RUN_IS_CREATED == false) || (JENKINS_CREATE_TEST_RUN == true && TEST_RUN_IS_CREATED == false)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
            Date date = new Date();
            client.setUser(username);
            client.setPassword(password);
            Map data = new HashMap();
            data.put("include_all", true);
            data.put("name", ENVIRONMENT+" API TestRun - " + formatter.format(date));
            data.put("description", "Description: Test run of automated API test for FBK");
            data.put("suite_id", suiteID);
            data.put("assignedto_id", 68);
            try {
                JSONObject c = (JSONObject) client.sendPost("add_run/" + projectID, data);
                Long run_Id = (Long) c.get("id");
                TEST_RUN_ID = run_Id;
                TEST_RUN_IS_CREATED = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onTestFailure(ITestResult result) {
        createTestRun();
        if ((CREATE_TEST_RUN == true && TEST_RUN_IS_CREATED == true) || (JENKINS_CREATE_TEST_RUN == true && TEST_RUN_IS_CREATED == true)) {
            String testCaseId = result.getInstance().getClass().getSimpleName().substring(5, 14);
            client.setUser(username);
            client.setPassword(password);
            HashMap data = new HashMap();
            data.put("status_id", TEST_CASE_FAILED_STATUS);
            data.put("comment", "Failure reason: "+result.getThrowable());
            try {
                Object response_Post_add_result_for_case = client
                        .sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + testCaseId + "", data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onTestSuccess(ITestResult result) {
        createTestRun();
        if (CREATE_TEST_RUN == true && TEST_RUN_IS_CREATED == true) {
            String testCaseId = result.getInstance().getClass().getSimpleName().substring(5, 14);
            client.setUser(username);
            client.setPassword(password);
            HashMap data = new HashMap();
            data.put("status_id", TEST_CASE_PASSED_STATUS);
            data.put("comment", "Test case successfully executed");
            try {
                Object response_Post_add_result_for_case = client
                        .sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + testCaseId + "", data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onTestSkipped(ITestResult result) {
        createTestRun();
        if (CREATE_TEST_RUN == true && TEST_RUN_IS_CREATED == true) {
            String testCaseId = result.getInstance().getClass().getSimpleName().substring(5, 14);
            client.setUser(username);
            client.setPassword(password);
            HashMap data = new HashMap();
            data.put("status_id", TEST_CASE_SKIPPED_STATUS);
            data.put("comment", "Skip reason: "+result.getThrowable());
            try {
                Object response_Post_add_result_for_case = client
                        .sendPost("add_result_for_case/" + TEST_RUN_ID + "/" + testCaseId + "", data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }

}
