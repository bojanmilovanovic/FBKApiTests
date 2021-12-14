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

import java.io.File;
import java.io.IOException;

import static org.testng.reporters.Files.readFile;

@Listeners({TestRailRunner.class})
public class TestC129430163GetAllFilteredTasks {

    @BeforeMethod(alwaysRun = true)
    public void testC129430163GetAllFilteredTasksPrecondition() throws IOException {
        TestC127321882CreateATaskWithAttachments testC127321882CreateATaskWithAttachments = new TestC127321882CreateATaskWithAttachments();
        testC127321882CreateATaskWithAttachments.testC127321882CreateATaskWithAttachments();
    }

    @Test(groups = {"tasks", "tp1"}, priority = 1)
    public void testC129430163GetAllFilteredTasks() throws IOException {

        // Generate token and set up the host
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/tasks/body/TestGetAllFilteredTasksBody.json");
        String requestBody = readFile(file);
        request.body(requestBody);

        // Response and assertion
        Response response = request.post("/tasks/search");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
        int listSize = response.jsonPath().getInt("pageResponse.size");
        String subject = "test_with_attachment";
        for(int i=0; i<listSize; i++){
            Assert.assertEquals(response.jsonPath().getString("tasks["+i+"].subject"), subject, "Subject of retrieved task is not according to the one set in filter");
        }

        // Global variable preparation for next tests
        Globals.TASK_ID = response.jsonPath().getString("tasks[0].id");
        Globals.TASK_FORMROUTE_ID = response.jsonPath().getString("tasks[0].relatedId");

    }

}
