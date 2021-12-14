package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.DBHelper;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestRailRunner.class})
public class TestC129277580CompleteTaskNegativeScenario {

    String MESSAGE_RESPONSE = "[Completion not possible for task in status - COMPLETED.]";

    @BeforeMethod(alwaysRun = true)
    public void testC129277580CompleteTaskNegativeScenarioPrecondition() throws Exception {
        TestC127321882CreateATaskWithAttachments testC127321882CreateATaskWithAttachments = new TestC127321882CreateATaskWithAttachments();
        testC127321882CreateATaskWithAttachments.testC127321882CreateATaskWithAttachments();
        TestC129430163GetAllFilteredTasks testC129430163GetAllFilteredTasks = new TestC129430163GetAllFilteredTasks();
        testC129430163GetAllFilteredTasks.testC129430163GetAllFilteredTasks();
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionTasks();
        dbHelper.runUpdate("update tsk_task set status = 'COMPLETED' where related_id = '"+ Globals.TASK_FORMROUTE_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"tasks", "tp1"}, priority = 2)
    public void testC129277580CompleteTaskNegativeScenario() {

        // Generate token and set up the host
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/admin-api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        String body = "{ }";
        request.body(body);

        // Response and assertion
        Response response = request.post("/tasks/"+Globals.TASK_ID+"/complete");
        Assert.assertEquals(response.getStatusCode(), 400, "Response status code is not 400");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true instead of false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text"), MESSAGE_RESPONSE, "Response message text is wrong");

    }

}
