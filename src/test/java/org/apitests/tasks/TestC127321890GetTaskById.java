package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
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


@Listeners({TestRailRunner.class})
public class TestC127321890GetTaskById {

    @BeforeMethod(alwaysRun = true)
    public void testC127321890GetTaskByIdPrecondition() throws IOException {
        TestC129430163GetAllFilteredTasks testC129430163GetAllFilteredTasks = new TestC129430163GetAllFilteredTasks();
        testC129430163GetAllFilteredTasks.testC129430163GetAllFilteredTasksPrecondition();
        testC129430163GetAllFilteredTasks.testC129430163GetAllFilteredTasks();
    }

    @Test(groups = {"tasks", "tp1"})
    public void testC127321890GetTaskById() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        Response response = request.get("/tasks/"+Globals.TASK_ID);
        Assert.assertEquals(response.getStatusCode(), 200, "Status is not 200");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
        Assert.assertFalse(response.jsonPath().getString("task.subject").isEmpty(), "Task does not have a subject field");
        File schema = new File("src/test/java/org/apitests/tasks/schema/GetTaskByIdSchema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

    }

}
