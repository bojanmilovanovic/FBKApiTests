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
public class TestC136343888DeleteATask {

    @BeforeMethod(alwaysRun = true)
    public void testC136343888DeleteATaskPrecondition() throws IOException {
        TestC129430163GetAllFilteredTasks testC129430163GetAllFilteredTasks = new TestC129430163GetAllFilteredTasks();
        testC129430163GetAllFilteredTasks.testC129430163GetAllFilteredTasksPrecondition();
        testC129430163GetAllFilteredTasks.testC129430163GetAllFilteredTasks();
    }

    @Test(groups = {"tasks", "tp1"})
    public void testC136343888DeleteATask() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        Response response = request.delete("/tasks/"+Globals.TASK_ID);
        Assert.assertEquals(response.getStatusCode(), 204, "Status is not 204");

    }

}
