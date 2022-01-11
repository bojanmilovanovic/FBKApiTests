package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC136343891AssignATask400 {

    @BeforeMethod(alwaysRun = true)
    public void testC136343891AssignATask400Precondition() throws IOException {
        // Create a task
        TestC127321882CreateATaskWithAttachments testC127321882CreateATaskWithAttachments = new TestC127321882CreateATaskWithAttachments();
        testC127321882CreateATaskWithAttachments.testC127321882CreateATaskWithAttachments();
        // Get the ID of the task
        TestC129430163GetAllFilteredTasks testC129430163GetAllFilteredTasks = new TestC129430163GetAllFilteredTasks();
        testC129430163GetAllFilteredTasks.testC129430163GetAllFilteredTasks();
        // Assign the task
        TestC127321886AssignATask testC127321886AssignATask = new TestC127321886AssignATask();
        testC127321886AssignATask.testC127321886AssignATask();
    }

    @Test(groups = {"tasks", "tp1"})
    public void testC136343891AssignATask400() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        String body = "{  \"userId\": \""+Globals.ASSIGNEE_ID+"\" }";
        request.body(body);

        // Response and assertion
        Response response = request.post("/tasks/"+Globals.TASK_ID+"/assign");
        Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "The value of the _status flag is not false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Current and new assignee must not be the same.", "Message text is not correct");

    }

}
